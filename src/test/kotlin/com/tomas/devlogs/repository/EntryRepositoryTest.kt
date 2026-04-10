package com.tomas.devlogs.repository

import com.tomas.devlogs.domain.entity.Entry
import com.tomas.devlogs.domain.entity.User
import com.tomas.devlogs.domain.enums.Difficulty
import com.tomas.devlogs.domain.enums.Mood
import com.tomas.devlogs.domain.enums.Visibility
import com.tomas.devlogs.domain.repository.EntryRepository
import com.tomas.devlogs.domain.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.UUID

@DataJpaTest
class EntryRepositoryTest @Autowired constructor(
    val entryRepository: EntryRepository,
    val userRepository: UserRepository
) {

    @Test
    fun `should save and retrieve entry`() {

        val user = userRepository.save(
            User(
                username = UUID.randomUUID().toString(),
                email = "${UUID.randomUUID()}@test.com",
                password = "password"
            )
        )

        val entry = Entry(
            title = "First DevLog",
            content = "Today I fixed Hibernate chaos.",
            difficulty = Difficulty.HARD,
            mood = Mood.MOTIVATED,
            visibility = Visibility.PUBLIC,
            user = user
        )

        entryRepository.save(entry)

        val all = entryRepository.findAll()

        Assertions.assertEquals(1, all.size)
        Assertions.assertEquals("First DevLog", all.first().title)
    }
}