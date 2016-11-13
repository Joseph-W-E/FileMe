package com.example.joey.fileme.request_image.server_image_view;

import java.util.List;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ServerImageViewFilter {

    private List<String> data;

    public interface Criteria {
        public List<String> meetCriteria(List<String> list);
    }

    public ServerImageViewFilter(List<String> data) {
        this.data = data;
    }

    public List<String> filterBy(Criteria criteria) {
        return criteria.meetCriteria(data);
    }
}
