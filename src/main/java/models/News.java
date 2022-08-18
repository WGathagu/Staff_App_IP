package models;

import java.util.Objects;

public class News {
    private String generalNews;
    private String departmentNews;
    private int departmentid;
    private int id;

        public News(String generalNews, String departmentNews, int departmentid) {
            this.generalNews = generalNews;
            this.departmentNews = departmentNews;
            this.departmentid = departmentid;
        }

        public String getgeneralNews() {
            return generalNews;
        }

        public String getdepartmentNews() {
            return departmentNews;
        }

        public int getId() {
            return id;
        }

        public int getDepartmentId() {
            return departmentid;
        }

        public void setgeneralNews(String generalNews) {
            this.generalNews = generalNews;
        }

        public void setdepartmentNews(String departmentNews) {
            this.departmentNews = departmentNews;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setdepartmentId(int departmentid) {
            this.departmentid = departmentid;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof News)) return false;
            News news = (News) o;
            return id == news.id &&
                    departmentid == news.departmentid &&
                    Objects.equals(generalNews, news.generalNews) &&
                    Objects.equals(departmentNews, news.departmentNews);
        }

        @Override
        public int hashCode() {
            return Objects.hash(generalNews, departmentNews, id, departmentid);
        }
}
