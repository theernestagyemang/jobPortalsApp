/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.ScheduledMeeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface ScheduledMeetingRepo extends PagingAndSortingRepository<ScheduledMeeting, Integer> {
    Optional<ScheduledMeeting> findByTransactionId(String trx);

    //public List<ScheduledMeeting> findLastFive(String trx);
    @Query(nativeQuery = true, value = "select  * FROM ScheduledMeeting where work_status = :status LIMIT 6")
    List<ScheduledMeeting> findByWorkStatusLimit(@Param("status") String status);
}
