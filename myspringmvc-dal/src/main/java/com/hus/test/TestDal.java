package com.hus.test;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:myspringmvc-servlet.xml"})

public class TestDal {
    /*@Test
    public void test2() {
        Mongo mongo = new Mongo();

        WriteConcern writeConcern = mongo.getWriteConcern();
        System.out.println(writeConcern);

        List<String> databaseNames = mongo.getDatabaseNames();
        for (int i = 0; i < databaseNames.size(); i++) {
            String s = databaseNames.get(i);
            System.out.println(s);
        }

        DB testDb = mongo.getDB("test");
        Set<String> testDbCollectionNames = testDb.getCollectionNames();
        for (String name : testDbCollectionNames) {
            System.out.println("test Collection:" + name.intern());
        }


        DB db = mongo.getDB("test");
        DBCollection users = db.getCollection("users");
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            System.out.println("next:" + cur.next());
            System.out.println("count:" + cur.count());
//            System.out.println("getCursorId:" + cur.getCursorId());
//            System.out.println("getBatchSize:" + cur.getBatchSize());
//            System.out.println("getCollection:" + cur.getCollection());
//            System.out.println("getKeysWanted:" + cur.getKeysWanted());
//            System.out.println("getLimit:" + cur.getLimit());
//            System.out.println("getOptions:" + cur.getOptions());
//            System.out.println("getQuery:" + cur.getQuery());
//            System.out.println("getReadPreference:" + cur.getReadPreference());
            System.out.println("getServerAddress:" + cur.getServerAddress());
            System.out.println(JSON.serialize(cur));
        }

    }*/

    private Mongo mg = null;
    private DB db;
    private DBCollection users;

    @Before
    public void init() {
        try {
            mg = new Mongo();
            //mg = new Mongo("localhost", 27017);
        } catch (MongoException e) {
            e.printStackTrace();
        }
        //获取temp DB；如果默认没有创建，mongodb会自动创建
        db = mg.getDB("temp");
        //获取users DBCollection；如果默认没有创建，mongodb会自动创建
        users = db.getCollection("users");
    }

    @After
    public void destory() {
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
        users = null;
        System.gc();
    }

    public void print(Object o) {
        System.out.println(o);
    }

    /**
     * <b>function:</b> 查询所有数据
     *
     * @author hoojo
     * @createDate 2011-6-2 下午03:22:40
     */
    private void queryAll() {
        print("查询users的所有数据：");
        //db游标
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            print(cur.next());
        }
    }


    @Test
    public void add() {
        //先查询所有数据
        queryAll();
        print("count: " + users.count());

        DBObject user = new BasicDBObject();
        user.put("name", "hoojo");
        user.put("age", 24);
        //users.save(user)保存，getN()获取影响行数
        //print(users.save(user).getN());

        //扩展字段，随意添加字段，不影响现有数据
        user.put("sex", "男");
        print(users.save(user).getN());

        //添加多条数据，传递Array对象
        print(users.insert(user, new BasicDBObject("name", "tom")).getN());

        //添加List集合
        List<DBObject> list = new ArrayList<DBObject>();
        list.add(user);
        DBObject user2 = new BasicDBObject("name", "lucy");
        user.put("age", 22);
        list.add(user2);
        //添加List集合
        print(users.insert(list).getN());

        //查询下数据，看看是否添加成功
        print("count: " + users.count());
        queryAll();
    }


    @Test
    public void remove() {
        WriteResult remove = users.remove(new BasicDBObject("_id", new ObjectId("5666778c269a822c98ccfd29")));
        System.out.println(remove);
    }

    @Test
    public void modify() {
        /*print("修改：" + users.update(new BasicDBObject("_id", new ObjectId("5666779c269a820a882bc4de")), new BasicDBObject("age", 99)));
        print("修改：" + users.update(
                new BasicDBObject("_id", new ObjectId("4dde2b06feb038463ff09043")),
                new BasicDBObject("age", 100),
                true,//如果数据库不存在，是否添加
                false//多条修改
        ));*/
        /*print("修改：" + users.update(
                new BasicDBObject("age", 104),
                new BasicDBObject("age", 105),
                false,//如果数据库不存在，是否添加
                false//false只修改第一天，true如果有多条就不修改
        ));*/

        //当数据库不存在就不修改、不添加数据，当多条数据就不修改
        /*print("修改多条：" + users.updateMulti(new BasicDBObject("age", 105), new BasicDBObject("name", "199")));*/
        //users.findAndModify(new BasicDBObject("age", 99), new BasicDBObject("age", 100));
        /*System.out.println(JSON.serialize(users.findOne(new BasicDBObject("name", "199"))));
        System.out.println(JSON.serialize(users.find(new BasicDBObject("name", "199"))));*/
//        System.out.println(users.find(new BasicDBObject("age", new BasicDBObject("$gte", 101))).toArray());
//        System.out.println(JSON.serialize(users.find(new BasicDBObject("age", new BasicDBObject("$gte", 101)))));
//        System.out.println(JSON.serialize(users.find(new BasicDBObject("age", 101))));
        //users.findAndRemove(new BasicDBObject("age", 100));
        // System.out.println(db.collectionExists("users"));
        /*DBObject options = new BasicDBObject();
        print(db.createCollection("accoun1t", options));*/
    }

}
