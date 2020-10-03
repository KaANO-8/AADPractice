package com.kaano8.androidsamples.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.kaano8.androidsamples.database.gift.GiftDatabaseDao
import com.kaano8.androidsamples.database.note.NoteDatabaseDao
import com.kaano8.androidsamples.models.gift.Gift
import com.kaano8.androidsamples.models.note.Note
import com.kaano8.androidsamples.repository.note.NoteRepository
import com.kaano8.androidsamples.ui.util.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var homeViewModel: HomeViewModel

    @Mock
    lateinit var noteRepository: NoteRepository

    @Mock
    lateinit var noteDatabaseDao: NoteDatabaseDao

    @Mock
    lateinit var giftDatabaseDao: GiftDatabaseDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        //noteRepository = NoteRepository(noteDatabaseDao, giftDatabaseDao)
        homeViewModel = HomeViewModel(noteRepository)

    }

    private fun getMockNotes(): MutableLiveData<List<Note>> {
        return MutableLiveData(
            listOf(
                Note(
                    noteId = 1,
                    note = "Thanks",
                    senderName = "Rahul",
                    recipientName = "Rohit",
                    gift = Gift(id = "1", name = "", amount = 1000.0, remark = "")
                )
            )
        )
    }


    @Test
    fun `test get notes`() {
        `when`(noteDatabaseDao.getAllNotes()).thenReturn(getMockNotes())
        homeViewModel.notes.observeOnce { noteList ->
            val note = noteList.first()
            assertEquals(note.senderName, "Rahul")
        }
    }

    @Test
    fun `test clear database`() = runBlocking{
        homeViewModel.clearDatabase()
        homeViewModel.clearDatabaseSnackBarEvent.observeOnce { assertTrue(it) }
        verify(noteRepository, times(1)).clear()
    }
}