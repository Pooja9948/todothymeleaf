package com.bridgelabz.note.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.note.dao.NoteDao;
import com.bridgelabz.note.model.NoteCollaborate;
import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.note.model.NoteLabel;
import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDao noteDao;

	
	public NoteDetails createNote(NoteDetails noteDetails) {
		return noteDao.createNote(noteDetails);
	}
	public List<NoteDetails> getAllNotes(UserDetails user){
		return noteDao.getAllNotes(user);
	}
	public int getUserByNoteId(int noteid) {
		return noteDao.getUserByNoteId(noteid);
	}
	public void deleteNote(int noteId) {
		noteDao.deleteNote(noteId);
	}
	public void updateNote(NoteDetails noteDetails) {
		noteDao.updateNote(noteDetails);
	}
	@Override
	public NoteDetails getNoteById(int noteId) {
		return noteDao.getNoteById(noteId);
	}
}
