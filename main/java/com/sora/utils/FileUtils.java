package com.sora.utils;

import com.sora.domain.AbsenceRecord;
import com.sora.domain.Keyword;
import com.sora.domain.Option;
import com.sora.domain.Question;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/6 21:26
 */
public class FileUtils {
    private static Properties prop = new Properties();
    static {
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean upload(MultipartFile file,String fileName) throws Exception{
        //定义上传的位置
        String path = prop.getProperty("file.path");
        File filePath = new File(path+fileName);
        file.transferTo(filePath);//上传文件
        return true;
    }

    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }

    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static List<Question> createQuestionsExcel(InputStream is, boolean isExcel2003) throws Exception{
        List<Question> questionList = null;
        Workbook wb = null;
        if (isExcel2003) {// 当excel是2003时,创建excel2003
            wb = new HSSFWorkbook(is);
        } else {// 当excel是2007时,创建excel2007
            wb = new XSSFWorkbook(is);
        }
        questionList = readQuestionsExcel(wb);
        return questionList;
    }

    public static List<Question> readQuestionsExcel(Workbook wb) throws Exception{
        int totalRows = 0;
        int totalCells = 0;
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Question> questionList = new ArrayList<Question>();
        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            Question question = new Question();
            // 循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        question.setContent(cell.getStringCellValue());
                    }else if (c == 1) {
                        List<Option> optionList = new ArrayList<>();
                        String optionsStr = cell.getStringCellValue();
                        char ch = 'A';
                        int index = optionsStr.indexOf(ch+"、");
                        while(index>-1 && index<optionsStr.length()){
                            int preIndex = index + 2;
                            ch = (char)(ch+1);
                            int postIndex = optionsStr.indexOf(ch+"、");
                            if(postIndex==-1){
                                postIndex = optionsStr.length();
                            }
                            String optionStr = optionsStr.substring(preIndex,postIndex);
                            if(optionStr.length()>0){
                                Option option = new Option();
                                option.setContent(optionStr);
                                optionList.add(option);
                            }
                            index = postIndex;
                        }
                        question.setOptionList(optionList);
                    }
                    else if (c == 2){
                        String answersStr = cell.getStringCellValue();
                        for(int i=0;i<answersStr.length();i++){
                            int answer = answersStr.charAt(i)-'A';
                            question.getOptionList().get(answer).setCorrect(true);
                        }
                    }
                    else if (c == 3){
                        String answerDescription = cell.getStringCellValue();
                        question.setAnswerDescription(answerDescription);
                    }
                    else if (c == 4){
                        List<Keyword> keywordList = new ArrayList<>();
                        String keywordsStr = cell.getStringCellValue();
                        while(keywordsStr.length()>0){
                            int preIndex = 0;
                            int postIndex = keywordsStr.indexOf("、");
                            if(postIndex == -1){
                                postIndex = keywordsStr.length();
                            }
                            String keywordStr = keywordsStr.substring(preIndex,postIndex);
                            if(keywordStr.length()>0){
                                Keyword keyword = new Keyword();
                                keyword.setName(keywordStr);
                                keywordList.add(keyword);
                            }
                            if(postIndex < keywordsStr.length()-1){
                                keywordsStr = keywordsStr.substring(postIndex+1);
                            }else{
                                keywordsStr = "";
                            }
                        }
                        question.setKeywordList(keywordList);
                    }
                }
            }
            // 添加到list
            questionList.add(question);
        }
        return questionList;
    }

    public static HSSFWorkbook exportAbsenceRecord(List<AbsenceRecord> absenceRecordList) throws Exception{
        // 第一步，创建一个workbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet hssfSheet = workbook.createSheet("sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = hssfSheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        //居中样式
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell hssfCell = null;
        String[] titles = {"班级","学号","姓名","时间"};
        for (int i = 0; i < titles.length; i++) {
            hssfCell = row.createCell(i);//列索引从0开始
            hssfCell.setCellValue(titles[i]);//列名1
            hssfCell.setCellStyle(hssfCellStyle);//列居中显示
        }

        for (int i = 0; i < absenceRecordList.size(); i++) {
            row = hssfSheet.createRow(i+1);
            AbsenceRecord absenceRecord = absenceRecordList.get(i);
            row.createCell(0).setCellValue(absenceRecord.getStudent().getAClass().getName());
            row.createCell(1).setCellValue(absenceRecord.getStudent().getWorkId());
            row.createCell(2).setCellValue(absenceRecord.getStudent().getName());
            Date time = absenceRecord.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            row.createCell(3).setCellValue(dateString);
        }
        // 第七步，将文件输出到客户端浏览器
        return workbook;
    }
}
