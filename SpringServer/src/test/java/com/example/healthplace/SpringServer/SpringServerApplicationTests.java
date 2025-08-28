package com.example.healthplace.SpringServer;

import com.example.healthplace.SpringServer.model.AnswerModel.Answer;
import com.example.healthplace.SpringServer.model.AnswerModel.AnswerDao;
import com.example.healthplace.SpringServer.model.AppointmentModel.AppointmentDao;
import com.example.healthplace.SpringServer.model.AppointmentModel.OnlineAppointment;
import com.example.healthplace.SpringServer.model.DoctorModel.Doctor;
import com.example.healthplace.SpringServer.model.DoctorModel.DoctorDao;
import com.example.healthplace.SpringServer.model.PriceModel.PriceList;
import com.example.healthplace.SpringServer.model.PriceModel.PriceListDao;
import com.example.healthplace.SpringServer.model.QuestionModel.Question;
import com.example.healthplace.SpringServer.model.QuestionModel.QuestionDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringServerApplicationTests {

	@Autowired
	private AppointmentDao appointmentDao;

	@Test
	void addAppointment() {
		OnlineAppointment onlineAppointment = new OnlineAppointment();
		onlineAppointment.setIdPatient(5);
		onlineAppointment.setIdDoctor(7);
		onlineAppointment.setService("BYPASS");
		onlineAppointment.setDate("2023-08-19");
		onlineAppointment.setTime("11:00");
		onlineAppointment.setDepartment("Cardiologie");
		onlineAppointment.setConfirmed(true);
		appointmentDao.saveAppointment(onlineAppointment);
	}

	@Autowired
	private PriceListDao priceListDao;
	@Test
	void addPrice() {
		PriceList priceList1 = new PriceList();
		priceList1.setService("b");
		priceList1.setPrice(600);
		priceList1.setDepartment("Endocrinologie");
		priceList1.setDiscountMedicalRaport(10);
		priceList1.setDiscountLoyaltyCard(5);
		priceListDao.savePriceList(priceList1);
	}

	@Autowired
	private DoctorDao doctorDao;

	@Test
	void addDoctor(){
		Doctor doctor = new Doctor();
		doctor.setFirstName("Gigel");
		doctor.setLastName("Lovi");
		doctor.setDepartment("Chirurgie");
		doctor.setSpeciality("Chirurgie plastica");
		doctor.setAddress("Str. Lojfg Popa, nr. 121");
		doctor.setSalary(2500);
		doctor.setEmploymentDate("2020-03-15");
		doctorDao.saveDoctor(doctor);
	}

	@Autowired
	private QuestionDao questionDao;

	@Test
	void addQuestion() throws IOException {
		Question question = new Question();
		question.setIdPatient(9);
		question.setPatientName("Ana Popescu");
		question.setQuestionText("Aparitita de sdfgfds");
		question.setIs_confirmed(false);
		questionDao.saveQuestion(question);
	}

	@Autowired
	private AnswerDao answerDao;
	@Test
	void addAnswer(){
		Answer answer = new Answer();
		answer.setIdQuestion(1);
		answer.setIdDoctor(2);
		answer.setAnswerText("Veniti urgent la medic");
		answerDao.saveAnswer(answer);
	}


}
