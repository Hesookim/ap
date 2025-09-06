package ap.exercises.finalProject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FileHandler {

    private static final String SAVE_LOCATION = "data/";
    private static final String BOOKS_FILE      = "books.json";
    private static final String STUDENTS_FILE   = "students.json";
    private static final String LIBRARIANS_FILE = "librarians.json";
    private static final String MANAGER_FILE    = "manager.json";
    private static final String BORROWINGS_FILE = "borrowings.json";
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private static Path resolve(String file) {
        return Paths.get(SAVE_LOCATION).resolve(file);
    }

    public FileHandler() {
        mapper.registerModule(new JavaTimeModule());
        try {
            Files.createDirectories(Paths.get(SAVE_LOCATION));
        } catch (IOException e) {
            System.err.println("ERROR: Failed to create directory '" + SAVE_LOCATION + "': " + e.getMessage());
        }
    }

    public void saveAll(Library lib) throws IOException {
        saveBooks(lib.getBookList());
        saveStudents(lib.getStudentList());
        saveLibrarians(lib.getOperatorList());
        saveManager(lib.getManager());
        saveBorrowings(lib.getBorrowBookList());
    }

    public Library loadAll(String libraryName) throws IOException {
        Library lib = new Library(libraryName);
        loadManager(lib);
        loadBooks(lib);
        loadStudents(lib);
        loadOperator(lib);
        loadBorrowings(lib);
        return lib;
    }

    private void saveBooks(List<Book> list) throws IOException {
        mapper.writeValue(resolve(BOOKS_FILE).toFile(), list);
    }

    private void loadBooks(Library lib) throws IOException {
        Path path = resolve(BOOKS_FILE);
        if (Files.notExists(path)) return;
        List<Book> books = mapper.readValue(path.toFile(), new TypeReference<List<Book>>() {});
        Operator dummyOperator = lib.getRandomOperator();
        if(dummyOperator == null) dummyOperator = new Operator("Dummy","Operator",1,"12345678");
        for (Book b : books) {
            lib.addBook(b, dummyOperator);
        }
    }

    private void saveStudents(List<Student> list) throws IOException {
        mapper.writeValue(resolve(STUDENTS_FILE).toFile(), list);
    }

    private void loadStudents(Library lib) throws IOException {
        Path path = resolve(STUDENTS_FILE);
        if (Files.notExists(path)) return;
        List<Student> students = mapper.readValue(path.toFile(), new TypeReference<List<Student>>() {});
        for (Student s : students) {
            lib.registerStudent(s);
        }
    }

    private void saveLibrarians(List<Operator> list) throws IOException {
        mapper.writeValue(resolve(LIBRARIANS_FILE).toFile(), list);
    }

    private void loadOperator(Library lib) throws IOException {
        Path path = resolve(LIBRARIANS_FILE);
        if (Files.notExists(path)) return;
        List<Operator> operators = mapper.readValue(path.toFile(), new TypeReference<List<Operator>>() {});
        for (Operator op : operators) {
            if(op.isRegistered()) op.setNewPassword(op.getPassword());
            lib.addOperator(op);
        }
    }

    public void saveManager(Manager m) throws IOException {
        if (m == null) return;
        mapper.writeValue(resolve(MANAGER_FILE).toFile(), m);
    }

    private void loadManager(Library lib) throws IOException {
        Path path = resolve(MANAGER_FILE);
        if (Files.notExists(path)) return;
        Manager m = mapper.readValue(path.toFile(), Manager.class);
        m.setLibrary(lib);
        lib.setManager(m);
    }

    private void saveBorrowings(List<BorrowBook> list) throws IOException {
        List<BorrowDTO> dtoList = list.stream().map(BorrowDTO::fromBorrowBook).toList();
        mapper.writeValue(resolve(BORROWINGS_FILE).toFile(), dtoList);
    }

    private void loadBorrowings(Library lib) throws IOException {
        Path path = resolve(BORROWINGS_FILE);
        if (Files.notExists(path)) return;
        List<BorrowDTO> dtoList = mapper.readValue(path.toFile(), new TypeReference<List<BorrowDTO>>() {});
        for (BorrowDTO dto : dtoList) {
            Book bk = lib.findBook(dto.bookISBN);
            Student st = lib.findStudent(dto.studentId);
            Operator lend = dto.lendingOperatorId != null ? lib.findOperator(dto.lendingOperatorId) : null;
            Operator rec = dto.receivingOperatorId != null ? lib.findOperator(dto.receivingOperatorId) : null;
            BorrowBook bb = new BorrowBook(bk, st, dto.borrowDate, dto.dueDate);
            if(lend != null) bb.confirmBorrow(lend);
            if(dto.returned && rec != null) bb.confirmReturn(rec, dto.returnDate);
            lib.getBorrowBookList().add(bb);
        }
    }

    static class BorrowDTO {
        public String bookISBN;
        public int studentId;
        public Integer lendingOperatorId;
        public LocalDate borrowDate;
        public LocalDate dueDate;
        public boolean returned;
        public LocalDate returnDate;
        public Integer receivingOperatorId;

        static BorrowDTO fromBorrowBook(BorrowBook bb) {
            BorrowDTO dto = new BorrowDTO();
            dto.bookISBN = bb.getBook().getISBN();
            dto.studentId = bb.getStudent().getId();
            dto.lendingOperatorId = bb.getLendingOperator() != null ? bb.getLendingOperator().getId() : null;
            dto.borrowDate = bb.getBorrowDate();
            dto.dueDate = bb.getDueDate();
            dto.returned = bb.isReturned();
            dto.returnDate = bb.getReturnedDate();
            dto.receivingOperatorId = bb.getReceivingOperator() != null ? bb.getReceivingOperator().getId() : null;
            return dto;
        }
    }
}
