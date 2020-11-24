package Database;

import android.provider.BaseColumns;

public class DatabaseMaster {

    public static class User implements BaseColumns {
        private String username , password;
        public User() {}

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_USERTYPE = "UserType";
    }

    public static class Comments implements BaseColumns {
        private String comment, gameName;
        private int rating;

        public Comments(){}

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public String getGameName() {
            return gameName;
        }

        public int getRating() {
            return rating;
        }

        public static final String TABLE_NAME = "Comments";
        public static final String COLUMN_NAME_GAMENAME = "GameName";
        public static final String COLUMN_NAME_COMMENTRATING = "Rating";
        public static final String COLUMN_NAME_COMMENT = "Comment";
    }

    public static class Game implements BaseColumns {
        private String gameName;
        private int year;

        public Game(){}

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getGameName() {
            return gameName;
        }

        public int getYear() {
            return year;
        }

        public static final String TABLE_NAME = "Games";
        public static final String COLUMN_NAME_GAMENAME = "GameName";
        public static final String COLUMN_NAME_GAMEYEAR = "GameYear";
    }
}
