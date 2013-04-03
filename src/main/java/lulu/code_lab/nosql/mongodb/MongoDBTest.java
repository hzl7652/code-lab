package lulu.code_lab.nosql.mongodb;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ReflectionDBObject;

public class MongoDBTest {

	private static Mongo m;

	@BeforeClass
	public static void init() throws Exception {
		m = new Mongo("localhost", 27017);
	}

	@Test
	public void helloWorld() throws Exception {
		DB db = m.getDB("test");
		System.out.println(db);
	}

	@Test
	public void pringAllDbName() {
		for (String dbName : m.getDatabaseNames()) {
			System.out.println(dbName);
		}
	}

	@Test
	public void printTestDbCollectionName() {
		DB db = m.getDB("test");
		for (String collectionName : db.getCollectionNames()) {
			System.out.println(collectionName);
		}
	}

	@Test
	public void insertDocument() {
		BasicDBObject doc = new BasicDBObject();
		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);

		DBObject info = BasicDBObjectBuilder.start("x", 203).append("y", 102).get();

		doc.put("info", info);
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");
		collection.insert(doc);

	}

	@Test
	public void testFindOne() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");
		DBObject obj = collection.findOne();
		System.out.println(obj);
	}

	@Test
	public void addMultipleDoc() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");
		for (int i = 0; i < 100; i++) {
			collection.insert(BasicDBObjectBuilder.start("i", i).get());

		}
	}

	@Test
	public void getCollectionCount() {
		System.out.println(m.getDB("test").getCollection("doc").count());
	}

	@Test
	public void getAllDoc() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");
		for (DBCursor cur = collection.find(); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void getSingleDocWithQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", 120);
		System.out.println(collection.findOne(query));

	}

	@Test
	public void getSingleDocWith$Operator() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$gt", 70));
		query.put("i", new BasicDBObject("$lt", 72));

		System.out.println(collection.findOne(query));
	}

	@Test
	public void getSet() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$gt", 90).append("$lt", 96));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void test$allQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$all", new int[] { 99 }));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	/**
	 * $exists 表明查询对象中是否有此属性.
	 */
	@Test
	public void test$existsQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$exists", false));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void test$modQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$mod", new int[] { 10, 1 }));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void test$neQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$ne", 5));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void test$inQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$in", new int[] { 1, 2, 3 }));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void testLimitQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$gt", 10));
		for (DBCursor cur = collection.find(query).skip(20).limit(10); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	@Test
	public void testRegexQuery() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("doc");

		BasicDBObject query = new BasicDBObject();
		query.put("name", new BasicDBObject("$regex", "DB$"));
		for (DBCursor cur = collection.find(query); cur.hasNext();) {
			System.out.println(cur.next());
		}
	}

	public class User extends ReflectionDBObject {
		String name;
		int age;
		Date birthDate;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}

	}

	@Test
	public void testSaveObject() {
		DB db = m.getDB("test");
		DBCollection collection = db.getCollection("user");
		User user = new User();
		user.age = 3;
		user.name = "papa";
		user.birthDate = new Date();
		collection.insert(user);
	}

}
