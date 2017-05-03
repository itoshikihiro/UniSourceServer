/**   
 * @Title: PDFGenerator.java 
 * @Package com.un.tool 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date May 3, 2017 1:20:30 AM 
 * @version V1.0   
 */  
package com.un.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.un.pojo.Activity;
import com.un.pojo.Course;
import com.un.pojo.Student;
import com.un.service.ActivitySvc;
import com.un.service.CoursesSvc;
import com.un.service.StuProfileSvc;
import com.un.service.impl.ActivitySvcImpl;
import com.un.service.impl.CourseSvcImpl;
import com.un.service.impl.StuProfileSvcImpl;

/** 
 * @ClassName: PDFGenerator 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date May 3, 2017 1:20:30 AM 
 *  
 */
public class PDFGenerator {
	
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = ".csv";

	public static void generate(String studentID) throws DocumentException, IOException{
		StuProfileSvc stuProfileService = new StuProfileSvcImpl();
		CoursesSvc courseService = new CourseSvcImpl();
		ActivitySvc activityService = new ActivitySvcImpl();
		Student s = stuProfileService.getStuProfile(studentID);
		ArrayList<Course> ac = courseService.readCList(studentID);
		ArrayList<Activity> aa = activityService.readAList(studentID);
		
		List<String> ponum=new ArrayList<String>();
		List<String> line=new ArrayList<String>();
		Iterator<Course> ic = ac.iterator();
		while(ic.hasNext())
		{
			Course c = ic.next();
				ponum.add(c.getCourseCode());
				line.add(c.getCourseName());
		}
		
		List<String> part=new ArrayList<String>();
		List<String> description=new ArrayList<String>();
		Iterator<Activity> ia = aa.iterator();
		while(ia.hasNext())
		{
			Activity a = ia.next();
			part.add(a.getActivityName());
			description.add(a.getActivityDescri());
		}

		//Create Document Instance
		Document document=new Document();

		//add Chinese font
		BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

		//Font headfont=new Font(bfChinese,10,Font.BOLD);
		Font keyfont=new Font(bfChinese,8,Font.BOLD);
		Font textfont=new Font(bfChinese,8,Font.NORMAL);

		//Create Writer associated with document
		PdfWriter.getInstance(document, new FileOutputStream(new File(FILEADD+studentID+"Resume.pdf")));

		document.open();

		//Seperate Page controller
		int recordPerPage=30;
		int fullPageRequired=ponum.size()/recordPerPage;
		int remainPage=ponum.size()%recordPerPage>1?1:0;
		int totalPage=fullPageRequired+remainPage;

		for(int j=0;j<totalPage;j++){
			document.newPage();

			//create page number
			String pageNo=leftPad("Page: "+(j+1)+" / "+totalPage,615);
			Paragraph pageNumber=new Paragraph(pageNo, keyfont) ;
			document.add(pageNumber);

			//create title image
			Image jpeg=Image.getInstance(FILEADD+"Uwhite.png");
			jpeg.setAlignment(Image.ALIGN_CENTER);
			jpeg.scaleAbsolute(323, 238);
			document.add(jpeg);

			//header information
			Table tHeader=new Table(2);
			float[] widthsHeader={2f,3f};
			tHeader.setWidths(widthsHeader);
			tHeader.setWidth(100);
			tHeader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);


			String studentName=s.getUsername();
			String schoolName="Ursinus College";
			String studentEmail=s.getUserID();
			String studentAge=Integer.toString(s.getAge());
			String studentDes=s.getDescription();
			String graduateYear=Integer.toString(s.getGraduateYear());


			Cell c1Header=new Cell(new Paragraph("Student name："+studentName,keyfont));
			tHeader.addCell(c1Header);
			c1Header=new Cell(new Paragraph("School："+schoolName,keyfont));
			tHeader.addCell(c1Header);
			c1Header=new Cell(new Paragraph("student email："+studentEmail,keyfont));
			tHeader.addCell(c1Header);
			c1Header=new Cell(new Paragraph("student age："+studentAge,keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("student description:   "+studentDes,keyfont));
			tHeader.addCell(c1Header);
			c1Header=new Cell(new Paragraph("graduate year:"+graduateYear,keyfont));
			tHeader.addCell(c1Header);
			document.add(tHeader);

			//record header field
			Table t=new Table(4);
			float[] widths={1.5f,1f,1f,1.5f};
			t.setWidths(widths);
			t.setWidth(100);
			t.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			Cell c1 = new Cell(new Paragraph("Course Code",keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Course Name",keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Activity Name",keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Activity Description",keyfont));
			t.addCell(c1); 

			//calculate the real records within a page ,to calculate the last record number of every page
			int maxRecordInPage= j+1 ==totalPage ? (remainPage==0?recordPerPage:(ponum.size()%recordPerPage)):recordPerPage;

			for(int i=j*recordPerPage;i<((j*recordPerPage)+maxRecordInPage);i++){
				Cell c2=new Cell(new Paragraph(ponum.get(i), textfont));
				t.addCell(c2);
				c2=new Cell(new Paragraph(line.get(i), textfont));
				t.addCell(c2);
				c2=new Cell(new Paragraph(part.get(i), textfont));
				t.addCell(c2);
				c2=new Cell(new Paragraph(description.get(i), textfont));
				t.addCell(c2);
			}
			document.add(t);

			if(j+1==totalPage){

				Paragraph foot11 = new Paragraph("This file is a basic resume"+printBlank(150)+"__________________________",keyfont);
				document.add(foot11);
				Paragraph foot12 = new Paragraph("Printed from UniSource, the system of Ursinus College."+printBlank(110)+schoolName+printBlank(40)+"version: 1.0",keyfont);
				document.add(foot12);
				HeaderFooter footer11=new HeaderFooter(foot11, true);
				footer11.setAlignment(HeaderFooter.ALIGN_BOTTOM);
				HeaderFooter footer12=new HeaderFooter(foot12, true);
				footer12.setAlignment(HeaderFooter.ALIGN_BOTTOM);
			}
		}
		document.close();
	}

	public static String leftPad(String str, int i) {
		int addSpaceNo = i-str.length();
		String space = ""; 
		for (int k=0; k<addSpaceNo; k++){
			space= " "+space;
		};
		String result =space + str ;
		return result;
	}

	public static void add(List<String> list,int num){
		for(int i=0;i<num;i++){
			list.add("test"+i);
		}
	}

	public static String printBlank(int tmp){
		String space="";
		for(int m=0;m<tmp;m++){
			space=space+" ";
		}
		return space;
	}
}

