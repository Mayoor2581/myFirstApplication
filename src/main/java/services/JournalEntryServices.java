package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import entity.JournalEntry;
import entity.UserEntry;
import repository.JournalEntryRepository;

@Service
public class JournalEntryServices {
	
	private final JournalEntryRepository journalEntryRepository;
	private final UserEntryServices userEntryServices;
	
	public JournalEntryServices(JournalEntryRepository journalEntryRepository, UserEntryServices userEntryServices){
		this.journalEntryRepository = journalEntryRepository;
		this.userEntryServices = userEntryServices;
	}
	
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
			throw new RuntimeException("cant save Entry");
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
				journalEntryRepository.deleteById(myId);
			}
		}
		catch(Exception e) {
			throw new RuntimeException("Failed to Delete",e);
		}
	}
	
	public List<JournalEntry> getJournalEntriesByName(String userName) {
		UserEntry userEntry = userEntryServices.findUserByName(userName);
		if(userEntry!=null) {
			return userEntry.getJournalEntries();
		}
		return new ArrayList<>();
		
	}
}

