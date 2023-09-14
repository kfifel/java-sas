package service;

import entities.Borrower;
import repository.BorrowerRepository;

import java.sql.SQLException;
import java.util.List;

public class BorrowerService {
    private BorrowerRepository borrowerRepository;

    public BorrowerService() {
        this.borrowerRepository = new BorrowerRepository();
    }

    public List<Borrower> read() {
        Logger.info("calling for getting all Borrowers ");
        return borrowerRepository.read();
    }

    public Borrower findById(int borrowerId) {
        Logger.info("calling for finding Borrowers by id :" + borrowerId);
        return borrowerRepository.findById(borrowerId);
    }

    public Borrower save(Borrower borrower) {
        Logger.info("calling for adding new Borrowers with full name :" + borrower.getFull_name());
        try {
            borrowerRepository.save(borrower);
        }catch (SQLException e) {
            Logger.error("Error", e);
        }
        return borrower;
    }

    public int countBorrowers() {
        try {
            return borrowerRepository.countBorrowers();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            return -1;
        }
    }
}
