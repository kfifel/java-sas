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
        return borrowerRepository.read();
    }

    public Borrower findById(int borrowerId) {
        return borrowerRepository.findById(borrowerId);
    }

    public Borrower save(Borrower borrower) {
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
