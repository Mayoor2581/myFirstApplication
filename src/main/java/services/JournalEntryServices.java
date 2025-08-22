package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import dto.JournalEntryDTO;
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
	public void saveEntry(JournalEntryDTO myEntry, String userName) {
		try {
			JournalEntry entry = new JournalEntry();
			entry.setTitle(myEntry.getTitle());
			entry.setContent(myEntry.getContent());
			entry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(entry);
			UserEntry user = userEntryServices.findUserByName(userName);
			if(user == null) {
				throw new JournalEntrySaveException("Not Found");
			}
			user.getJournalEntries().add(saved);
			userEntryServices.saveUserEntry(user);
		}
		catch(Exception e) {
			throw new JournalEntrySaveException("cant save Entry", e);
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
			throw new JournalEntryDeleteException("Failed to Delete",e);
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

class JournalEntrySaveException extends RuntimeException {
    public JournalEntrySaveException(String message, Throwable cause) {
        super(message, cause);
    }
    public JournalEntrySaveException(String message) {
    	super(message);
    }
}

class JournalEntryDeleteException extends RuntimeException {
    public JournalEntryDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

