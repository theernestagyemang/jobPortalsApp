package com.ao.schoolerp.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
public class SystemLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "login_time")
    private Time loginTime;

    @Column(name = "logout_time")
    private Time logoutTime;

    public SystemLog(Integer id, Date loginDate, Time loginTime, Time logoutTime) {
        this.id = id;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public SystemLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Time getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Time loginTime) {
        this.loginTime = loginTime;
    }

    public Time getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Time logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemLog systemLog = (SystemLog) o;
        return Objects.equals(id, systemLog.id) && Objects.equals(loginDate, systemLog.loginDate) && Objects.equals(loginTime, systemLog.loginTime) && Objects.equals(logoutTime, systemLog.logoutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginDate, loginTime, logoutTime);
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", loginDate=" + loginDate +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                '}';
    }
}
