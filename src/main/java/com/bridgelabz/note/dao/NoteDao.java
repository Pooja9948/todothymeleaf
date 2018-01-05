package com.bridgelabz.note.dao;

import java.util.List;
import java.util.Set;

import com.bridgelabz.note.model.NoteCollaborate;
import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.note.model.NoteLabel;
import com.bridgelabz.note.model.NoteUrl;
import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public interface NoteDao {
	
	/**
	 * @param note
	 * @return passing the parameter to create the note
	 */
	public NoteDetails createNote(NoteDetails note);

	public List<NoteDetails> getAllNotes(UserDetails user);
	
	int getUserByNoteId(int noteid);
	public void deleteNote(int noteId);
	public void updateNote(NoteDetails noteDetails);
	public NoteDetails getNoteById(int noteId);
	public List<NoteDetails> getArchiveNotes(UserDetails user);
	public List<NoteDetails> getTrashNotes(UserDetails user);
}
