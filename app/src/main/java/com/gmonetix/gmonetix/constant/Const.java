package com.gmonetix.gmonetix.constant;

public class Const {

    public static final String url = "http://gmonetix.com/wp-json/wp/v2/posts/?filter[category_name]=android&per_page=100&fields=id,date,link,title,better_featured_image";

    public static final String get_categories_url = "http://gmonetix.com/wp-json/wp/v2/categories?fields=id,description,name";

    public static final String get_all_posts_of_category_url = "http://gmonetix.com/wp-json/wp/v2/posts?categories=CATEGORY_ID&fields=id,date,link,title,better_featured_image";

    public static final String get_content_by_id = "http://www.gmonetix.com/wp-json/wp/v2/posts/POST_ID?fields=content";

    public static final String pages = "http://www.gmonetix.com/wp-json/wp/v2/pages/PAGE_ID?fields=content";

}