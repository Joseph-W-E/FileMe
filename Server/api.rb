require 'sinatra'
require 'sqlite3'
require 'json'

db = SQLite3::Database.open 'database.db'

get '/get/all' do
  table = db.execute "select * from files;"
  JSON.pretty_generate convert_table_to_array_of_hashes table, %w(id name file)
end

get '/get/:name' do
  table = db.execute "select * from files where name = '#{name}';"
  JSON.pretty_generate convert_table_to_array_of_hashes table, %w(name file)
end

post '/post' do

end


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
