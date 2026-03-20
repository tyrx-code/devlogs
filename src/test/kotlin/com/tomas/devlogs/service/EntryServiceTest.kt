//package com.tomas.devlogs.service
//
//import com.tomas.devlogs.domain.entity.Entry
//import com.tomas.devlogs.domain.entity.Tag
//import com.tomas.devlogs.domain.enums.Difficulty
//import com.tomas.devlogs.domain.enums.Mood
//import com.tomas.devlogs.domain.enums.Visibility
//import com.tomas.devlogs.domain.repository.EntryRepository
//import com.tomas.devlogs.domain.service.EntryService
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertNotNull
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.util.AssertionErrors.assertEquals
//
//@ExtendWith(MockitoExtension::class)
//class EntryServiceTest {
//
//    @Mock
//    lateinit var entryRepository: EntryRepository
//
//    @InjectMocks
//    lateinit var entryService: EntryService
//
//    @Test
//    fun `should create entry`() {
//        val entry = Entry(
//            title = "Service Layer Test",
//            content = "Testing business logic.",
//            difficulty = Difficulty.MEDIUM,
//            mood = Mood.TIRED,
//            visibility = Visibility.PUBLIC,
//            tags = mutableSetOf()
//        )
//
//        `when`(entryRepository.save(entry)).thenReturn(entry.copy(id = 1L))
//
//        val saved = entryService.create(entry)
//
//        assertNotNull(saved.id)
//        assertEquals("Service Layer Test", saved.title)
//    }
//}
