public class JLoginCourier {


        // ключ name стал полем типа String
        private String login;
        // ключ link стал полем типа String
        private String password;


        public JLoginCourier(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public JLoginCourier() {
        }

        // геттер для поля name
        public String getLogin() {
            return login;
        }
        // сеттер для поля name
        public void setlLogin(String login) {
            this.login = login;
        }

        // геттер для поля link
        public String getPassword() {
            return password;
        }

        // сеттер для поля link
        public void setPassword(String password) {
            this.password = password;
        }
    }


