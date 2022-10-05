package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "package_transaction")
public class PackageTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "entry_date")
    private Date entryDate;
    private Double amount;
    @Column(name = "payment_method")
    private String paymentMethode;
    @Column(name = "user_type")
    private String userType;
    private boolean status;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private PackageSubscribe packageSubscribe;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    public PackageTransaction(Integer id, Date entryDate, Double amount, String paymentMethode, String userType,
                              boolean status, String transactionId, PackageSubscribe packageSubscribe, Users users) {
        this.id = id;
        this.entryDate = entryDate;
        this.amount = amount;
        this.paymentMethode = paymentMethode;
        this.userType = userType;
        this.status = status;
        this.transactionId = transactionId;
        this.packageSubscribe = packageSubscribe;
        this.users = users;
    }

    public PackageTransaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethode() {
        return paymentMethode;
    }

    public void setPaymentMethode(String paymentMethode) {
        this.paymentMethode = paymentMethode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PackageSubscribe getPackageSubscribe() {
        return packageSubscribe;
    }

    public void setPackageSubscribe(PackageSubscribe packageSubscribe) {
        this.packageSubscribe = packageSubscribe;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

