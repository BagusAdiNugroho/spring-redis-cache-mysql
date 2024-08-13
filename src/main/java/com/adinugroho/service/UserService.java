package com.adinugroho.service;

import com.adinugroho.entity.User;
import com.adinugroho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Metode getUserById dianotasi dengan @Cacheable, yang berarti hasil dari metode ini akan disimpan dalam cache dengan kunci yang ditentukan oleh parameter id.
    // Jika metode ini dipanggil lagi dengan id yang sama, nilai yang di-cache akan dikembalikan tanpa memanggil metode tersebut lagi.
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Metode saveUser dianotasi dengan @CachePut, yang berarti hasil dari metode ini akan disimpan dalam cache dengan kunci yang ditentukan oleh id dari objek User yang disimpan.
    // Ini memastikan bahwa cache diperbarui setiap kali pengguna disimpan.
    @CachePut(value = "users", key = "#result.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Metode deleteUser dianotasi dengan @CacheEvict, yang berarti entri cache dengan kunci yang ditentukan oleh parameter id akan dihapus dari cache ketika metode ini dipanggil.
    // Ini memastikan bahwa cache tetap konsisten dengan data di database.
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
