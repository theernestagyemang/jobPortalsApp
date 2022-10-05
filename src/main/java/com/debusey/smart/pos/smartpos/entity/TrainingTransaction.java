package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "training_transaction")
public class TrainingTransaction implements Serializable {
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

    //    Number of Participants
    private Integer participants;
    private boolean status;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinColumn(name = "training_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private TrainingSchedule trainingSchedule;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    public TrainingTransaction(Integer id, Date entryDate, Double amount, String paymentMethode, String userType, Integer participants, boolean status, String transactionId, TrainingSchedule trainingSchedule, Users users) {
        this.id = id;
        this.entryDate = entryDate;
        this.amount = amount;
        this.paymentMethode = paymentMethode;
        this.userType = userType;
        this.participants = participants;
        this.status = status;
        this.transactionId = transactionId;
        this.trainingSchedule = trainingSchedule;
        this.users = users;
    }

    public TrainingTransaction() {
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

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
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

    public TrainingSchedule getTrainingSchedule() {
        return trainingSchedule;
    }

    public void setTrainingSchedule(TrainingSchedule trainingSchedule) {
        this.trainingSchedule = trainingSchedule;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingTransaction that = (TrainingTransaction) o;
        return status == that.status && Objects.equals(id, that.id) && Objects.equals(entryDate, that.entryDate) && Objects.equals(amount, that.amount) && Objects.equals(paymentMethode, that.paymentMethode) && Objects.equals(userType, that.userType) && Objects.equals(participants, that.participants) && Objects.equals(transactionId, that.transactionId) && Objects.equals(trainingSchedule, that.trainingSchedule) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryDate, amount, paymentMethode, userType, participants, status, transactionId, trainingSchedule, users);
    }
}