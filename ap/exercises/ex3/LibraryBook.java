package ap.exercises.ex3;

    class LibraryBooks {
        private String bookName;
        private String bookAuthor;
        private int bookYear;
        private int bookPages;
        private int copies;

        public LibraryBooks(String name, String bookAuthor, int bookYear, int bookPages, int copies) {
            this.bookName = name;
            this.bookAuthor = bookAuthor;
            this.bookYear = bookYear;
            this.bookPages = bookPages;
            this.copies = copies;
        }

        public String getBookName() {
            return bookName;
        }

        public String getBookAuthor() {
            return bookAuthor;
        }

        public int getBookYear() {
            return bookYear;
        }

        public int getBookPages() {
            return bookPages;
        }

        public int getCopies() {
            return copies;
        }

        public void setCopies(int copies) {
            this.copies = copies;
        }
    }