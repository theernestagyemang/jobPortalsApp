/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

/**
 * @author AlhassanHussein
 */

@Controller
public class EducationHistoryController {


    @Autowired
    private EducationService service;
    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private ITSkillsService skillsService;

    @Autowired
    private WorkExperienceService wkService;

    @Autowired
    private PositionsService posService;

    @PostMapping("/admin/upload-bulk-education-update")
    @ResponseBody
    public String uploadFileUpdate(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<EducationalExperience> list = convertFromExcelUpdate(in);
            return service.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "FAILED";
    }

    @PostMapping("/admin/upload-bulk-skills-update")
    @ResponseBody
    public String uploadSkillsUpdate(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<ITSkills> list = convertFromExcelUpdate2(in);
            return skillsService.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "FAILED";
    }

    @PostMapping("/admin/upload-bulk-workhistory-update")
    @ResponseBody
    public String uploadWorkHistoryUpdate(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<WorkExperience> list = convertFromExcelWk(in);
            System.out.println("list prepared..." + list.size());
            return wkService.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "FAILED";
    }


    public List<EducationalExperience> convertFromExcelUpdate(InputStream is) {
        try {
            List<EducationalExperience> productList;
            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                productList = new ArrayList<>();

                DataFormatter formater = new DataFormatter();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    EducationalExperience product = new EducationalExperience();

                    //edu_hist_id	certificate_name	institution_name	course_description	year_completed	job_seeker_id	date_added														
                    try {

                        int cellIdx = 0;
                        while (cellsInRow.hasNext()) {
                            Cell currentCell = cellsInRow.next();

                            switch (cellIdx) {

                                case 0:
                                    String val = formater.formatCellValue(currentCell);
                                    Integer id = Integer.valueOf(val);
                                    product.setId(id);
                                    break;

                                case 1:
                                    String qualification = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(qualification)) {
                                        product.setQualificationReceived(qualification);
                                    }
                                    break;

                                case 2:
                                    String institution_name = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(institution_name)) {
                                        product.setInstitutionName(institution_name);
                                    }
                                    break;

                                case 3:
                                    String course_description = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(course_description)) {
                                        product.setCourseDescription(course_description);
                                    }
                                    break;

                                case 4:
                                    String year_completed = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(year_completed)) {
                                        product.setYearGraduated(findCompletedYear(year_completed));
                                    }
                                    break;

                                case 5:
                                    String job_seeker_id = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(job_seeker_id)) {
                                        JobSeeker seeker = findJobSeeker(job_seeker_id);
                                        if (seeker != null) {
                                            product.setJobSeekerId(seeker);
                                        }

                                    }
                                    break;


                                default:
                                    break;

                            }

                            cellIdx++;
                        }

                        product.setTransactionId(JsfUtil.generateSerial());
                        if (product.getJobSeekerId() != null) {
                            productList.add(product);
                        }

                    } catch (NumberFormatException e) {
                    } catch (Exception e) {

                    }

                }
            }

            return productList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<WorkExperience> convertFromExcelWk(InputStream is) {
        try {
            List<WorkExperience> productList;
            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                productList = new ArrayList<>();

                DataFormatter formater = new DataFormatter();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    WorkExperience product = new WorkExperience();

                    //0work_history_id	1company_name	2position	3description	4date_started	5date_ended	6currently_here	7job_seeker_id	8date_added								

                    try {

                        int cellIdx = 0;
                        while (cellsInRow.hasNext()) {
                            Cell currentCell = cellsInRow.next();

                            switch (cellIdx) {

                                case 0:
                                    String work_history_id = formater.formatCellValue(currentCell);
                                    Integer id = Integer.valueOf(work_history_id);
                                    product.setId(id);
                                    break;

                                case 1:
                                    String company_name = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(company_name)) {
                                        product.setCompanyName(company_name);
                                    }
                                    break;

                                case 2:
                                    String position = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(position)) {
                                        String des = truncateWords(position);
                                        product.setJobTitle(des);
                                        product.setDesignation(des);
                                    }
                                    break;

                                case 3:
                                    String description = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(description)) {
                                        product.setDescriptions(description);
                                    }
                                    break;

                                case 4:
                                    String date_started = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(date_started)) {

                                        LocalDate dateStarted = JsfUtil.convertToLocalDate(date_started);
                                        if (dateStarted != null) {
                                            String monthStarted = dateStarted.getMonth().toString();
                                            Integer yearStarted = dateStarted.getYear();

                                            product.setMonthStartedWork(monthStarted);
                                            product.setYearStartedWork(yearStarted);
                                        }

                                    }
                                    break;


                                case 5:
                                    String date_ended = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(date_ended)) {

                                        LocalDate dateEnded = JsfUtil.convertToLocalDate(date_ended);
                                        if (dateEnded != null) {
                                            String monthStopped = dateEnded.getMonth().toString();
                                            Integer yearStoped = dateEnded.getYear();

                                            product.setMonthStopedWork(monthStopped);
                                            product.setYearStopedWork(yearStoped);
                                        }

                                    }
                                    break;

                                case 6:
                                    String currently_here = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(currently_here)) {
                                        if (currently_here.equalsIgnoreCase("1")) {
                                            product.setStillWorkThere("Yes");
                                        } else {
                                            product.setStillWorkThere("No");
                                        }

                                    }
                                    break;
                                case 7:
                                    String job_seeker_id = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(job_seeker_id)) {
                                        JobSeeker seeker = findJobSeeker(job_seeker_id);
                                        if (seeker != null) {
                                            product.setJobSeekerId(seeker);
                                        }

                                    }
                                    break;


                                default:
                                    break;

                            }

                            cellIdx++;
                        }

                        product.setTransactionId(JsfUtil.generateSerial());
                        productList.add(product);


                    } catch (NumberFormatException e) {
                    } catch (Exception e) {

                    }

                }
            }

            return productList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<ITSkills> convertFromExcelUpdate2(InputStream is) {
        try {
            List<ITSkills> productList;
            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                productList = new ArrayList<>();

                DataFormatter formater = new DataFormatter();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    ITSkills product = new ITSkills();

                    //ITSkills														
                    try {

                        int cellIdx = 0;
                        while (cellsInRow.hasNext()) {
                            Cell currentCell = cellsInRow.next();

                            switch (cellIdx) {

                                case 0:
                                    String job_seeker_id = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(job_seeker_id)) {
                                        JobSeeker seeker = findJobSeeker(job_seeker_id);
                                        if (seeker != null) {
                                            product.setJobSeekerId(seeker);
                                        }

                                    }
                                    break;
                                case 1:
                                    String qualification = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(qualification)) {
                                        product.setSkill(qualification);
                                    }
                                    break;

                                case 2:
                                    String proficiency = formater.formatCellValue(currentCell);
                                    if (!"EMPTY_FIELD".equals(proficiency)) {
                                        product.setProficiency(Integer.parseInt(proficiency));
                                    }
                                    break;
                                default:
                                    break;

                            }

                            cellIdx++;
                        }
                        if (product.getJobSeekerId() != null) {
                            productList.add(product);
                        }

                    } catch (NumberFormatException e) {
                    } catch (Exception e) {

                    }

                }
            }

            return productList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private JobSeeker findJobSeeker(String strId) {
        try {
            Integer id = Integer.parseInt(strId);
            Optional<JobSeeker> oppSeeker = seekerService.findById(id);
            if (oppSeeker.isPresent()) {
                return oppSeeker.get();
            }

            return null;
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        return null;
    }

    private Integer findCompletedYear(String year_completed) {
        try {
            Integer id = Integer.parseInt(year_completed);
            return id;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Positions findPositions(String txt) {
        Optional<Positions> opp = posService.findByName(txt);
        if (opp.isPresent()) {
            return opp.get();
        }
        Positions pos = new Positions();
        pos.setName(txt);
        pos.setEntryDate(new Date());
        pos.setGrade(0);
        pos.setCatIcon("home");

        if (posService.save(pos)) {
            return pos;
        }

        return null;
    }

    private String truncateWords(String position) {
        if (position.length() > 255) {
            position = position.substring(0, 254);
            return position;
        }
        return position;
    }


}
