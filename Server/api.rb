require 'rubygems'
require 'sinatra'
require 'sqlite3'
require 'json'

configure do
  set :bind, '65.110.227.87'
  set :bind, '0.0.0.0'
end

db = SQLite3::Database.open 'database.db'

# GET REQUESTS

get '/get/all' do
  table = db.execute "select * from images;"
  JSON.pretty_generate convert_table_to_array_of_hashes table, %w(id name description date data)
end

get '/get/:name' do
  table = db.execute "select * from images where name = '#{params[:name]}';"
  JSON.pretty_generate convert_table_to_array_of_hashes(table, %w(id name description date data))[0]
end

# POST REQUESTS

post '/post' do
  return_message = {}

  payload = JSON.parse request.body.read, :symbolize_names => true

  if (payload.has_key?(:name) && payload.has_key?(:date) && payload.has_key?(:data))
    payload[:name] = payload[:name].tr("'", "")
    if (payload.has_key?(:description))
      payload[:description] = payload[:description].tr("'", "")
      db.execute "insert into images (name, description, date, data) values ('#{payload[:name]}', '#{payload[:description]}', #{payload[:date]}, '#{payload[:data]}');"
    else
      db.execute "insert into images (name, date, data) values ('#{payload[:name]}', #{payload[:date]}, #{payload[:data]});"
    end
    return_message[:status] = '201 - Successfully created new file.'
  else
    return_message[:status] = '400 - Error: Invalid JSON file.'
  end

  JSON.pretty_generate return_message
end

# HELPER METHODS

def convert_table_to_array_of_hashes(table, columns)
  return_array = []
  table.each do |row|
    hash = Hash.new
    row.each_with_index do |element, index|
      hash[columns[index]] = element
    end
    return_array.push hash
  end
  return return_array
end
