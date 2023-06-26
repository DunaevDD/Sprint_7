//public class JCourier {
    public class JCourier {
        // ключ name стал полем типа String
        private String login;
        // ключ link стал полем типа String
        private String password;

        private String firstName;

        // конструктор со всеми параметрами
        public JCourier(String login, String password, String firstName) {
            this.login = login;
            this.password = password;
            this.firstName = firstName;
        }

        // конструктор без параметров
        public JCourier() {
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
            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
        }

