/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos;

import com.debusey.smart.pos.smartpos.controller.PageSetupController;
import com.debusey.smart.pos.smartpos.db.*;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * @author Admin
 */
public class JsfUtil {

    public static String SAVED_JOB_APPLIED = "applied";
    public static String SAVED_JOB_SAVED = "saved";


    public static String DOCUMENTS = Paths.get("").toAbsolutePath().toString();

    public static String SHORTLISTED = "Shortlisted";
    public static String ON_HOLD = "On hold";
    public static String REJECTED = "Rejected";
    public static String PENDING = "Pending";
    public static String HIRED = "Hired";

    public static Date convertToDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//2020-04-30
        if (dateInString == null) {
            return null;
        }

        if (dateInString.isEmpty()) {
            return null;
        }
        try {

            Date date = formatter.parse(dateInString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date convertToDate(String dateInString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);//2020-04-30
        if (dateInString == null) {
            return null;
        }

        if (dateInString.isEmpty()) {
            return null;
        }
        try {

            Date date = formatter.parse(dateInString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getCountries() {
        List<String> list = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {
            Locale obj = new Locale("", countryCode);
            list.add(obj.getDisplayCountry());
        }
        return list;
    }

    /**
     * ROLE_ADMIN
     * ROLE_RECRUITER
     * ROLE_COMPANY
     * ROLE_JOBSEEKER
     * ROLE_HEAD_RECRUITER
     *
     * @param tx
     * @return
     */

    public static Integer appLevel(String tx) {
        switch (tx) {
            case "ROLE_JOBSEEKER":
                return 1;

            case "ROLE_COMPANY":
                return 2;

            case "ROLE_RECRUITER":
                return 3;

            case "ROLE_HEAD_RECRUITER":
                return 4;

            case "ROLE_ADMIN":
                return 5;

            default:
                return 0;
        }

    }

    public static List<String> createListFromString(String message) {
        if (message == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(message.split(","));
    }


    public static boolean isStaffUser(Principal principal) throws Exception {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        if (user == null) {
            return false;
        }
        String usertype = user.getUserType();

        if (usertype == null) {
            return false;
        }
        return "Staff".equalsIgnoreCase(usertype);
    }

    public static Users findOnline(Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        if (loginedUser == null) {
            return null;
        }
        return loginedUser.getUser();

    }

    public static String generateSerial() {
        return (UUID.randomUUID().toString()).toUpperCase();
    }

    public static String getInvoice() {
        return (UUID.randomUUID().toString()).toUpperCase().substring(1, 12);
    }

    public static String convertFromDate(Date date, String pattern) {
        try {
            Format formatter = new SimpleDateFormat(pattern);
            String s = formatter.format(date);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertFromDate(Date date) {

        if (date == null) {
            return null;
        }
        try {
            return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage simpleResizeImage(byte[] bytes, int targetWidth, int targetHeight) throws Exception {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage originalImage = ImageIO.read(is);

        //   return Scalr.resize(originalImage, targetWidth,height);
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

    public static String removeWhiteSpace(String fileName) {
        if (!fileName.contains(" ")) {
            return fileName;
        }

        String newName = fileName.replaceAll("\\s", "");
        return newName;
    }

    public static byte[] convertToByte(BufferedImage bi) {
        try {
            // convert BufferedImage to byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (IOException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public static void saveToDisk(DbFile dbfile) {
        try {
            File file = new File("uploads");
            if (!file.exists()) {
                file.mkdir();
            }

            Path path = Paths.get(file.toPath() + "/" + dbfile.getFileName());
            Files.write(path, dbfile.getUploadedFile());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Path findPath(String fileName) {
        File file = new File("uploads");
        if (!file.exists()) {
            file.mkdir();
        }
        Path path = Paths.get(file.toPath() + "/" + fileName);
        return path;
    }

    public static boolean deleteFromDisk(String fileName) {
        File file = new File("uploads/" + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


    public static boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    public static Users authUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        MyUserDetails loginedUser = (MyUserDetails) authentication.getPrincipal();
        if (loginedUser == null) {
            return null;
        }

        return loginedUser.getUser();
    }

    public static void createStaffModel(Model model, Users user) {
    }

    public static void createEmployerModel(Model model, Users user) {
    }

    public static String[] createList(List<String> emails) {

        String[] myArray = new String[emails.size()];
        emails.toArray(myArray);

        return myArray;
    }

    public static String findfile(String file) {

        if (file != null) {
            File uploadedFile = new File("uploads/" + file);
            if (uploadedFile.exists()) {
                return file;
            }
        }
        return "default-avatar.png";
    }

    public static String convertToString(List<String> list) {
        return list.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static PostedBy createPostedBy(Employer emp) {
        PostedBy postedBy = new PostedBy();
        if (emp == null) {
            postedBy.setCompanyName("Reputable Company");
            return postedBy;
        }
        String compName = emp.getCompanyName();
        if (compName == null) {
            compName = "Reputable Company";
        }
        postedBy.setCompanyName(compName);
        postedBy.setEmail(emp.getEmail());
        postedBy.setRequesterName(emp.getContactName());
        postedBy.setRequesterContact(emp.getContactPhoneNumber());
        postedBy.setTelephone(emp.getPhoneNumber());

        return postedBy;
    }

    public static void loadSubscription(Model model, List<Subscription> subList, Integer option) {
        switch (option) {
            case 1:
                loadSeekerModel(model, subList);
                break;
            default:
                loadEmployerModel(model, subList);
                break;
        }
    }

    public static void loadSeekerModel(Model model, List<Subscription> subList) {
        if (subList.isEmpty()) {
            model.addAttribute("basic", new Subscription(1, "Basic", "Default Basic Subscription", BigDecimal.ZERO));
            model.addAttribute("optional", new Subscription(1, "Optional", "Default optional Subscription", BigDecimal.ZERO));
            model.addAttribute("premium", new Subscription(1, "Premium", "Default premium Subscription", BigDecimal.ZERO));


            model.addAttribute("premiumMsg", "O Job prefrence");
            model.addAttribute("optionalMsg", "");
            model.addAttribute("basicMsg", "");
            model.addAttribute("list", subList);

            return;

        }

        Subscription optional = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName() != null)
                .filter(u -> u.getName().equalsIgnoreCase("Seeker Optional"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> optionalItems = JsfUtil.createListFromString(optional.getMessage());
        List<String> optionalMsg = new ArrayList<>();
        optionalItems.forEach(optionalMsg::add);
        Integer pref = optional.getJobprefCount();
        if (pref == null) {
            pref = 0;
        }
        optionalMsg.add(pref + " Job Prefrence ");


        Subscription basic = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName() != null)
                .filter(u -> u.getName().equalsIgnoreCase("Seeker Basic"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> basicItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> basicMsg = new ArrayList<>();
        basicItems.forEach(basicMsg::add);

        Integer basicpref = basic.getJobprefCount();
        if (basicpref == null) {
            basicpref = 0;
        }

        basicMsg.add(basicpref + " Job Prefrence ");


        Subscription premium = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName() != null)
                .filter(u -> u.getName().equalsIgnoreCase("Seeker Premium"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> premiumItems = JsfUtil.createListFromString(premium.getMessage());
        List<String> premiumMsg = new ArrayList<>();
        premiumItems.forEach(premiumMsg::add);

        Integer premiumpref = premium.getJobprefCount();
        if (premiumpref == null) {
            premiumpref = 0;
        }

        premiumMsg.add(premiumpref + " Job Prefrence ");


        model.addAttribute("premium", premium);
        model.addAttribute("basic", basic);
        model.addAttribute("optional", optional);

        model.addAttribute("premiumMsg", premiumMsg);
        model.addAttribute("optionalMsg", optionalMsg);
        model.addAttribute("basicMsg", basicMsg);

        model.addAttribute("list", subList);

    }

    public static void loadEmployerModel(Model model, List<Subscription> subList) {
        if (subList.isEmpty()) {

            model.addAttribute("basic", new Subscription(1, "Basic", "Default Basic Subscription", BigDecimal.ZERO));
            model.addAttribute("optional", new Subscription(1, "Optional", "Default optional Subscription", BigDecimal.ZERO));
            model.addAttribute("premium", new Subscription("Premium", "Default premium Subscription", BigDecimal.ZERO));


            model.addAttribute("premiumMsg", "CV Downloads: " + 0);
            model.addAttribute("optionalMsg", "CV Downloads: " + 0);
            model.addAttribute("basicMsg", "CV Downloads: " + 0);

            model.addAttribute("list", subList);

            return;

        }
        Subscription optional = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Optional"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> optionalItems = JsfUtil.createListFromString(optional.getMessage());
        List<String> optionalMsg = new ArrayList<>();
        optionalItems.forEach(optionalMsg::add);

        Integer opcount = optional.getCvCount();
        if (opcount == null) {
            opcount = 0;
        }

        optionalMsg.add("CV Downloads: " + opcount);
        model.addAttribute("optionalMsg", optionalMsg);

        Subscription basic = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Basic"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> basicItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> basicMsg = new ArrayList<>();
        basicItems.forEach(basicMsg::add);

        Integer basicCount = basic.getCvCount();
        if (basicCount == null) {
            basicCount = 0;
        }
        basicMsg.add("CV Downloads: " + basicCount);
        model.addAttribute("basicMsg", basicMsg);


        Subscription premium = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Premium"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));


        List<String> premiumItems = JsfUtil.createListFromString(premium.getMessage());
        List<String> premiumMsg = new ArrayList<>();
        premiumItems.forEach(premiumMsg::add);

        Integer premiumCount = premium.getCvCount();
        if (premiumCount == null) {
            premiumCount = 0;
        }
        premiumMsg.add("CV Downloads: " + premiumCount);
        model.addAttribute("premiumMsg", premiumMsg);

        model.addAttribute("premium", premium);
        model.addAttribute("basic", basic);
        model.addAttribute("optional", optional);


        model.addAttribute("list", subList);

    }

    public static String findDetails(String email) {
        if (email == null) {
            return "Not Provided";
        }
        return email;
    }

    public static BigDecimal findDetails(BigDecimal email) {
        if (email == null) {
            return BigDecimal.ZERO;
        }
        return email;
    }

    public static Integer findDetails(Integer email) {
        if (email == null) {
            return 0;
        }
        return email;
    }

    public static String findResume(JobSeeker c) {
        String cv = c.getCvFileName();
        if (cv != null) {
            return cv;
        }
        String resume = c.getUploadedResume();
        if (resume != null) {
            return resume;
        }

        return "Not Provided";

    }

    public static List<String> convertToList(String commaSeparated) {

        List<String> items = Stream.of(commaSeparated.split(","))
                .map(String::trim)
                .collect(toList());
        return items;
    }


    public static LocalDate convertToLocalDate(String date) {
        try {
            if (date == null) {
                return null;
            }
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            //convert String to LocalDate
            return LocalDate.parse(date, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            return null;
        }

    }

    public static LocalDate convertToLocalDate(String date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            //convert String to LocalDate
            return LocalDate.parse(date, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Sms sendSMS(String to, String content, RestTemplate restTemplate) {
        try {
            //String url = "https://smsc.hubtel.com/v1/messages/send?clientsecret=umegayxu&clientid=uqslgtsx&from=The CoPEC&to=233545821801&content=This+Is+A+Test+Message";
            String apiUrl = "https://smsc.hubtel.com/v1/messages/send";
            String clientsecret = "umegayxu";
            String clientId = "uqslgtsx";
            String from = URLEncoder.encode("XJOBS", StandardCharsets.UTF_8.toString());
            String message = URLEncoder.encode(content, StandardCharsets.UTF_8.toString());

            URL url = new URL(apiUrl + "?clientsecret=" + clientsecret + "&clientid=" + clientId + "&from=" + from + "&to=" + to + "&content=" + message);
            Sms sms = restTemplate.getForObject(url.toURI(), Sms.class);

            return sms;
        } catch (MalformedURLException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String fetchText(String fileName) {

        fileName = fileName + ".txt";
        File file = new File("uploads/" + fileName);
        if (!file.exists()) {
            return "Page Is Under Construction";
        }
        String path = file.getAbsolutePath();


        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;

        } catch (IOException ex) {
            Logger.getLogger(PageSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static double convertToDouble(BigDecimal val) {
        if (val == null) {
            return 0;
        }
        return val.doubleValue();
    }

    public static Integer jobSeekerId(JobSeeker seeker) {
        if (seeker == null) {
            return 0;
        }
        return seeker.getId();
    }

    public static String convertToString(LocalDate localDate) {
        try {
            if (localDate == null) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
            return convertToString(localDate, "dd-MM-yyyy");
        }
    }

    public static String convertToString(LocalDate localDate, String pattern) {
        try {
            if (localDate == null) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToString(LocalDateTime localDate) {
        try {
            if (localDate == null) {
                return "";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
            return convertToString(localDate, "dd-MM-yyyy");
        }
    }

    public static String convertToString(LocalDateTime localDate, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {

        }
        return null;
    }

    public static String convertToLocalDate(LocalDateTime localDate, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDate, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertLocalDateToString(LocalDate localDate, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formattedString = localDate.format(formatter);
            return formattedString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime convertToLocalDateTime(String str, String pattern) {
        if (str == null) {
            return null;
        }

        if ("undefined".equals(str)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Employee currentStaff(Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            if (user == null) {
                return null;
            }
            return user.getStaffId();
        } catch (NullPointerException n) {
            return null;
        }
    }

    public static String sendPayment(String url, String token, BigDecimal amount, String ref,
                                     String redirectUrl, String backUrl, String uniqueRef,
                                     String serviceDescription, String serviceDate, String currency) throws IOException, NullPointerException {
        String transToken = null;

        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            //conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");


            String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +

                    "<API3G>\n" +
                    "<CompanyToken>" + token + "</CompanyToken>\n" +
                    "<Request>createToken</Request>\n" +
                    "<Transaction>\n" +
                    "<PaymentAmount>" + amount + "</PaymentAmount>\n" +
                    "<PaymentCurrency>" + currency + "</PaymentCurrency>\n" +
                    "<CompanyRef>49FYTA09</CompanyRef>\n" +
                    "<RedirectURL>" + redirectUrl + "</RedirectURL>\n" +
                    "<BackURL>" + backUrl + "</BackURL>\n" +
                    "<CompanyRefUnique>0</CompanyRefUnique>\n" +
                    "<PTL>15</PTL>\n" +
                    "<PTLtype>hours</PTLtype>\n" +
                    "<customerFirstName></customerFirstName>\n" +
                    "<customerLastName></customerLastName>\n" +
                    "<customerZip></customerZip>\n" +
                    "<customerAddress>Accra</customerAddress>\n" +
                    "<customerCity>Accra</customerCity>\n" +
                    "<customerCountry>GH</customerCountry>\n" +
                    "<customerDialCode></customerDialCode>\n" +
                    "<customerPhone>0709947947</customerPhone>\n" +
                    "<customerEmail></customerEmail>\n" +
                    "<AllowRecurrent></AllowRecurrent>\n" +
                    "</Transaction>\n" +
                    "<Services>\n" +
                    "<Service>\n" +
                    "<ServiceType>3854</ServiceType>\n" +
                    "<ServiceDescription>" + serviceDescription + "</ServiceDescription>\n" +
                    "<ServiceDate>" + serviceDate + "</ServiceDate>\n" +
                    "</Service>\n" +
                    "</Services>\n" +
                    "</API3G>";


            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = conn.getOutputStream();
            stream.write(out);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }


            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response.toString())));

            NodeList errNodes = doc.getElementsByTagName("API3G");
            if (errNodes.getLength() > 0) {
                Element err = (Element) errNodes.item(0);
                Node abc = err.getElementsByTagName("TransToken").item(0);
                if (abc != null) {
                    transToken = abc.getTextContent();
                }

            }
            conn.disconnect();
            in.close();

            return transToken;
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return transToken;

    }

    public static Integer createYearsOfExp(String exp) {
        try {
            if (exp == null) {
                return 0;
            }
            switch (exp) {
                case "1 Years":
                    return 1;
                case "2 Years":
                    return 2;
                case "3 Years":
                    return 3;
                case "4 Years":
                    return 4;
                case "5 Years":
                    return 5;
                case "6 Years":
                    return 6;
                case "7 Years":
                    return 7;
                case "8 Years":
                    return 8;
                case "9 Years":
                    return 9;
                case "10 Years":
                    return 10;
                case "10 Years +":
                    return 10;
                case "22":
                    return 22;

                default:
                    return 0;
            }

        } catch (Exception e) {
            return 0;
        }
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());

    }

    public static LocalDate convertToDate(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static DbFile uploadFile(MultipartFile file) {
        if (file == null) {
            return null;
        }
        try {
            Date today = new Date();
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (originalFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileName = renameFile(originalFileName);
            //String id,String fileName, String fileType, byte[] uploadedFile
            return new DbFile(fileName, file.getContentType(), file.getBytes());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String createFileName2(MultipartFile research) {
        if (research == null) {
            return null;
        }
        DbFile data = uploadFile(research);
        if (data != null) {
            saveToDisk(data);
            return data.getFileName();
        }
        return null;
    }

    public static UploadFileMeta createFileName(MultipartFile research) {
        if (research == null) {
            return null;
        }
        DbFile data = uploadFile(research);
        if (data != null) {
            saveToDisk(data);
            return new UploadFileMeta(data);
        }
        return null;
    }

    public static String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static String renameFile(DbFile dbfile) {

        String appendName = String.valueOf((new Date()).getTime());
        String originalFileName = dbfile.getFileName();
        String extention = JsfUtil.getExtensionByApacheCommonLib(originalFileName);
        return appendName + "." + extention;
    }

    public static String renameFile(String originalFileName) {
        String appendName = String.valueOf((new Date()).getTime());
        String extention = JsfUtil.getExtensionByApacheCommonLib(originalFileName);
        return appendName + "." + extention;
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
