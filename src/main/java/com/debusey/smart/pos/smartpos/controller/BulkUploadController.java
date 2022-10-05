/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.service.EmployerService;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Admin
 */

@Controller
public class BulkUploadController {
    private static final String UPLOADED_FILE_SESSION = "file-upload";

    @Autowired
    private JobSeekerService service;

    @Autowired
    private JobsService pservice;


    @Autowired
    private EmployerService empService;


//    public static List<JobSeeker> excelToTutorials(InputStream is) {
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(is);
//
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rows = sheet.iterator();
//
//            List<JobSeeker> productList = new ArrayList<>();
//
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//
//                // skip header
//                if (rowNumber == 0) {
//                  rowNumber++;
//                  continue;
//                }
//
//            Iterator<Cell> cellsInRow = currentRow.iterator();
//            JobSeeker product = new JobSeeker();
//            DataFormatter formater  = new DataFormatter();
//                int cellIdx = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//                    
//                    //full_name
//                    //address 1
//                    //primary_contact 2//
//                    //current_company 3
//                    //current_location4
//                    //C Sal	5
//                    //Min Sal	6
//                    //expected_salary	7
//                    //CV FN	8 Edu Level	email	HQL	Home Twn	pic_file_name	proffesional_titile	profile_strenght	profile_summary	exp
//
//                    //full_name	
//                    //address	1
//                    //primary_contact	2
//                    //current_company	3
//                    //current_location	4
//                    //C Sal	5
//                    //Min Sal	6
//                    //expected_salary	7
//                    //CV FN	8
//                    //Edu Level8 9
//                    //email	10
//                    //HQL	11
//                    //Home Twn	12
//                    //pic_file_name	13
//                    //proffesional_titile 14	
//                    //profile_strenght	15
//                    //profile_summary 16
//                    //exp 17
//
//
//                  switch (cellIdx) {
//                  
//                      
//                      case 0: 
//                          product.setFullName(formater.formatCellValue(currentCell));
//                        break;
//                        
//                      case 1: product.setAddress(formater.formatCellValue(currentCell));
//                         break;
//                        
//                      case 2: product.setPrimaryContact(formater.formatCellValue(currentCell));
//                        break;
//                        
//                      case 3: product.setCurrentCompany(formater.formatCellValue(currentCell));
//                        break;
//                        
//                      case 4: product.setCurrentLocation(formater.formatCellValue(currentCell));
//                        break;
//                        
//                       case 5: product.setCurrentSalary(BigDecimal.valueOf((double)currentCell.getNumericCellValue()));
//                        break;
//                        
//                        case 6: product.setExpMinSalary(BigDecimal.valueOf((double)currentCell.getNumericCellValue()));
//                        break;
//                        
//                        case 7: product.setExpMinSalary(BigDecimal.valueOf((double)currentCell.getNumericCellValue()));
//                        break;
//                          
//                        
//                        case 8: product.setCvFileName(formater.formatCellValue(currentCell));
//                        break;
//                        
//                        case 9: product.setEducationLevel(formater.formatCellValue(currentCell));
//                        break;
//                        
//                        case 10: product.setEmail(formater.formatCellValue(currentCell));
//                        break;
//                        
////                      case 14: 
////                          String st = formater.formatCellValue(currentCell);
////                          Date startDate = JsfUtil.convertToDate(st,"dd-MMM-yy");
////                          product.setStarDate(startDate);
////                        
////                        break;
////
////                          
////                      case 15: 
////                          String ed = formater.formatCellValue(currentCell);
////                          Date endDate = JsfUtil.convertToDate(ed,"dd-MMM-yy");
////                          product.setEndDate(endDate);
////                        break;
////                          
////                      case 16: product.setAffiliation(formater.formatCellValue(currentCell));
////                        break;
////                          
////                      case 17: product.setAccredStatus(formater.formatCellValue(currentCell));
////                        break;
////                   
////                      default:
////                        break;
//                  }
//
//                  cellIdx++;
//                }
//
//                productList.add(product);
//            }
//
//            workbook.close();
//
//            return productList;
//    } catch (IOException e) {
//         throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//    }
//  }
//    


//    
//    @GetMapping("/admin/institution-upload")
//    public String uploadInstitution(Model model, Principal principal){
//        System.out.println("start error..."+errorTrace);
////        if(listOfItems==null){
////            listOfItems = new ArrayList<>();
////        }
//        List<Institutions> list = service.findAll();
//        model.addAttribute("list", list);
//        model.addAttribute("errorTrace", errorTrace);
//        
//        return "admin/institution-upload";
//    }
//    
//    
//    @GetMapping("/admin/program-upload")
//    public String uploadProgram(Model model, Principal principal){
//        
//       
//        List<Programs> list=pservice.findAll();
//        
//        model.addAttribute("list", list);
//        model.addAttribute("errorTrace", progErrorTrace);
//        
//        return "admin/program-upload";
//    }
//
//    private void saveBulk(List<Institutions> list) {
//        
//        if(list==null){
//            return ;
//        }
//        
//        if(list.isEmpty()){
//            return ;
//        }
//        boolean status =service.saveAll(list);
//        if(status){
//            errorTrace=null;
//        }
//      
//        
//    }
//    
//    
//    private void saveBulk2(List<Programs> list) {
//        
//        if(list==null){
//            return ;
//        }
//        
//        if(list.isEmpty()){
//            return ;
//        }
//        
//        boolean status =pservice.saveAll(list);
//        if(status){
//            progErrorTrace=null;
//        }
//        
//    }
//    
//    @GetMapping("/clearList")
//    @ResponseBody
//    public boolean clearList(){
//        return false;
//    }
}
