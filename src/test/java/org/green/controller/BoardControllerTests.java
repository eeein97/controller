package org.green.controller;

import org.green.mapper.BoardMapperTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//Servlet�� ServletContext�� �̿��ϱ� ���Ͽ� ����
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
					"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTests {
		@Setter(onMethod_= {@Autowired})
		private WebApplicationContext ctx;
		//���� Mvc url�� �Ķ���� ���� ���������� ����ϴ� �� ó�� ���� ��Ʈ�ѷ��� ������ �� �� ����
		//MockMvcRequestBuilders�� ����� get������ �׽�Ʈ�� �� ����
		private MockMvc mockMvc;
		
		//�޼ҵ�
		//��� �׽�Ʈ ���� �Ź� ����Ǵ� �żҵ�� ����
		@Before
		public void setup() {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		}
		
		//��Ϻ��� �׽�Ʈ
		@Test
		public void testList() throws Exception {
			log.info(
					//get��� ����
					mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
					.andReturn()
					.getModelAndView()
					.getModelMap()
					);
		}
		@Test
		public void testRegiser() throws Exception {
			String resultPage = 
					mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
					.param("title", "�ݿ��� ����")
					.param("content", "�ݿ��� ����")
					.param("writer", "�ݱ�")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		}
		//�Խñ� 1�� ��ȸ �׽�Ʈ
		@Test
		public void testGet() throws Exception {
			log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/get")
					.param("bno", "6"))
					.andReturn()
					.getModelAndView()
					.getModel()
					);
		}
			//�Խñ� ����  �׽�Ʈ
			@Test
			public void testModify() throws Exception {
				String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
						.param("bno", "4")
						.param("title", "�ݿ��� ���� ����")
						.param("content", "�ݿ��� ���� ����")
						.param("writer", "����"))
						.andReturn().getModelAndView().getViewName();
				log.info(resultPage);		
			}
			//���� �׽�Ʈ
			@Test
			public void testRemove() throws Exception {
				String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
						.param("bno", "4"))
						.andReturn()
						.getModelAndView()
						.getViewName();
				log.info(resultPage);
			}
}
