package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public User authenticate(String username, String password) {
        try {
            User user = entityManager.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            // Пользователь не найден или ошибка
        }
        return null;
    }

    public boolean register(String username, String email, String password) {
        try {
            // Проверяем, существует ли пользователь с таким username
            User existingUser = entityManager.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Проверяем, существует ли пользователь с таким именем
            if (existingUser != null || isEmailTaken(username)) {
                return false; // Email или имя уже используется
            }

            // Создаем нового пользователя
            User newUser = new User(username, email, password);
            entityManager.persist(newUser); // Сохраняем пользователя в базу
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку для диагностики
            return false;
        }
    }

    private boolean isEmailTaken(String email) {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }
}