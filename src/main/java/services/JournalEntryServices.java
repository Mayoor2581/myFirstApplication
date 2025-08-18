package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.JournalEntry;
import entity.UserEntry;
import repository.JournalEntryRepository;

@Service
public class JournalEntryServices {
	@Autowired 
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserEntryServices userEntryServices;
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	//@Transactional
	public void saveEntry(JournalEntry myEntry, String userName) {
		try {
			myEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(myEntry);
			UserEntry user = userEntryServices.findUserByName(userName);
			user.getJournalEntries().add(saved);
			userEntryServices.saveUserEntry(user);
		}
		catch(Exception e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
		
	}
	
	public void saveEntry(JournalEntry myEntry) {
		journalEntryRepository.save(myEntry);
	}
	
	public Optional<JournalEntry> findById(Object myId) {
		return journalEntryRepository.findById((ObjectId) myId);
	}
	
	public void deleteById(ObjectId myId, String userName) {
		try {
			UserEntry user = userEntryServices.findUserByName(userName);
			boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
			if(removed) {
				userEntryServices.saveUserEntry(user);
				journalEntryRepository.deleteById((ObjectId) myId);
			}
		}
		catch(Exception e) {
			throw new RuntimeException("Failed to Delete",e);
		}
	}
	
	public List<JournalEntry> GetJournalEntriesByName(String userName) {
		UserEntry userEntry = userEntryServices.findUserByName(userName);
		if(userEntry!=null) {
			List<JournalEntry> journalEntry = userEntry.getJournalEntries();
			return journalEntry;
		}
		return new ArrayList<>();
		
	}
}

