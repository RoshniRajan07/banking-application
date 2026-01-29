package com.wipro.bank.service;
import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;
import com.wipro.bank.util.InsufficientFundsException;
public class BankService {
	public String checkBalance(String accountNumber) {

	    BankDAO bankDAO = new BankDAO();
	    boolean isValid = bankDAO.validateAccount(accountNumber);
	    if (!isValid) {
	        return "ACCOUNT NUMBER IS INVALID";
	    }
	    float balance = bankDAO.findBalance(accountNumber);
	    return "BALANCE IS:" + balance;
	}
public String transfer(TransferBean transferBean) {
    if (transferBean == null) {
        return "INVALID";
    }
    BankDAO bankDAO = new BankDAO();
    String fromAcc = transferBean.getFromAccountNumber();
    String toAcc = transferBean.getToAccountNumber();
    float amount = transferBean.getAmount();
    boolean isFromValid = bankDAO.validateAccount(fromAcc);
    boolean isToValid = bankDAO.validateAccount(toAcc);

    if (!isFromValid || !isToValid) {
        return "INVALID ACCOUNT";
    }

    try {
        float fromBalance = bankDAO.findBalance(fromAcc);

        if (fromBalance < amount) {
            throw new InsufficientFundsException();
        }
        boolean debit = bankDAO.updateBalance(fromAcc, fromBalance - amount);
        float toBalance = bankDAO.findBalance(toAcc);
        boolean credit = bankDAO.updateBalance(toAcc, toBalance + amount);
        boolean transferInserted = bankDAO.transferMoney(transferBean);
        if (debit && credit && transferInserted) {
            return "SUCCESS";
        } else {
            return "FAILURE";
      }
    } catch (InsufficientFundsException e) {
        return "INSUFFICIENT FUNDS";
    }
}}