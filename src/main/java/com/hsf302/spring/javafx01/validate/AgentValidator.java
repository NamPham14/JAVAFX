package com.hsf302.spring.javafx01.validate;

import com.hsf302.spring.javafx01.dao.AgentDAO;
import com.hsf302.spring.javafx01.dao.impl.AgentDAOImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.util.function.Predicate;

public class AgentValidator {
    private static final AgentDAO agentDAO;

    // Khởi tạo tĩnh để tạo instance AgentDAO
    static {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JakartaPersistenceUnit");
        agentDAO = new AgentDAOImpl(entityManagerFactory);
    }

    // Xác thực các trường đăng nhập (email và mật khẩu)
    public static void validateLogin(TextField emailField, TextField passwordField, ValidationSupport validationSupport) {
        // Validator cho email không trống
        Predicate<String> emailNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> emailNotEmptyValidator = Validator.createPredicateValidator(
                emailNotEmptyPredicate,
                "Email không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(emailField, emailNotEmptyValidator);

        // Validator cho mật khẩu không trống
        Predicate<String> passwordNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> passwordNotEmptyValidator = Validator.createPredicateValidator(
                passwordNotEmptyPredicate,
                "Mật khẩu không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(passwordField, passwordNotEmptyValidator);
    }

    // Xác thực các trường đăng ký (tên đại lý, email, địa chỉ, mật khẩu)
    public static void validateRegistration(TextField agentNameField, TextField emailField, TextField addressField, TextField passwordField, ValidationSupport validationSupport) {
        // Validator cho tên đại lý không trống
        Predicate<String> agentNameNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> agentNameNotEmptyValidator = Validator.createPredicateValidator(
                agentNameNotEmptyPredicate,
                "Tên đại lý không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(agentNameField, agentNameNotEmptyValidator);

        // Validator cho email không trống
        Predicate<String> emailNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> emailNotEmptyValidator = Validator.createPredicateValidator(
                emailNotEmptyPredicate,
                "Email không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(emailField, emailNotEmptyValidator);

        // Validator cho tính duy nhất của email
        Predicate<String> emailUniquePredicate = email -> {
            if (email == null || email.trim().isEmpty()) {
                return true; // Để validator không trống xử lý trường hợp này
            }
            try {
                return agentDAO.selectByEmail(email) == null;
            } catch (Exception e) {
                e.printStackTrace(); // Ghi lại lỗi để gỡ lỗi
                return false; // Làm thất bại xác thực nếu có lỗi
            }
        };
        Validator<String> emailUniqueValidator = Validator.createPredicateValidator(
                emailUniquePredicate,
                "Email đã tồn tại. Vui lòng sử dụng email khác.",
                Severity.ERROR
        );
        validationSupport.registerValidator(emailField, emailUniqueValidator);

        // Validator cho địa chỉ không trống
        Predicate<String> addressNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> addressNotEmptyValidator = Validator.createPredicateValidator(
                addressNotEmptyPredicate,
                "Địa chỉ không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(addressField, addressNotEmptyValidator);

        // Validator cho mật khẩu không trống
        Predicate<String> passwordNotEmptyPredicate = s -> s != null && !s.trim().isEmpty();
        Validator<String> passwordNotEmptyValidator = Validator.createPredicateValidator(
                passwordNotEmptyPredicate,
                "Mật khẩu không được để trống.",
                Severity.ERROR
        );
        validationSupport.registerValidator(passwordField, passwordNotEmptyValidator);
    }
}