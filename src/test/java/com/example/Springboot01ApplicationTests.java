package com.example;

import com.example.dao.ItemDao;
import com.example.dao.RecordDao;
import com.example.dao.UserDao;
import com.example.domain.Item;
import com.example.domain.Record;
import com.example.domain.User;
import com.example.service.RecordService;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Springboot01ApplicationTests {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordService recordService;

//    @Test
//    void testById() {
//        Item item = itemDao.getById(1);
//        System.out.println(item.toString());
//    }

//    @Test
//    void testGetAll() {
//        List<Item> items = itemDao.getAll();
//        System.out.println(items.toString());
//    }
//
//    @Test
//    void testUserGetAll() {
//        List<User> users = userDao.getAll();
//        System.out.println(users.toString());
//    }
//
//    @Test
//    void testGetByName() {
//        List<Item> items = itemDao.getByName("a");
//        System.out.println(items);
//    }
//
//    @Test
//    void testUserGetById() {
//        User user = new User();
//        user.setUsername("123456");
//        user.setPassword("444");
//        User user1 = userDao.getByAll(user);
//        System.out.println(user1.toString());
//
//
//    }
//
//    @Test
//    void testRecordGet() {
//        List<Record> records = recordDao.get(1, 3);
//        List<Record> records1 = recordService.get(1,3);
////        for (Record record : records) {
////            System.out.println(record.toString());
////        }
//        for (Record record : records1) {
//            System.out.println(record.toString());
//        }
//    }
//
//    @Test
//    void testSave() {
//        Date date = new Date();
//        Record record = new Record();
//        record.setCreated(date);
//        record.setTo_user(3L);
//        record.setContent("bbbb");
//        record.setUsed(false);
//        record.setFrom_user(4L);
////        System.out.println(recordDao.save(record));
//        System.out.println(recordService.save(record));
//    }
//
//    @Test
//    void testFindUnread() {
////        List<Record> records = recordDao.findUnread(1, 3);
////        for (Record record : records) {
////            System.out.println(record.toString());
////        }
//        List<Record> records = recordService.findUnread(1, 3);
//        for (Record record : records) {
//            System.out.println(record.toString());
//        }

//    }
}
