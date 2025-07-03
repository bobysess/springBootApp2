package com.bobysess.springBootApp2.service.jaxws;

import java.time.Instant;

import jakarta.jws.WebService;

@WebService
public class BankAccountService {
    
    public BankAccount createUser (User user) {
        return null; 
    }


    public static class User {
        private String firstname;
        private String lastname;
        private int old;
        
        public User(String firstname, String lastname, int old) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.old = old;
        }
        
        public String getFirstname() {
            return firstname;
        }
        
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        
        public String getLastname() {
            return lastname;
        }
        
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        
        public int getOld() {
            return old;
        }
        
        public void setOld(int old) {
            this.old = old;
        }
    }

    public static class BankAccount {
        private String iban;
        private String bic;
        private Instant createdAt;
        
        public BankAccount(String iban, String bic, Instant createdAt) {
            this.iban = iban;
            this.bic = bic;
            this.createdAt = createdAt;
        }
        
        public String getIban() {
            return iban;
        }
        
        public void setIban(String iban) {
            this.iban = iban;
        }
        
        public String getBic() {
            return bic;
        }
        
        public void setBic(String bic) {
            this.bic = bic;
        }
        
        public Instant getCreatedAt() {
            return createdAt;
        }
        
        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }
    }
}
