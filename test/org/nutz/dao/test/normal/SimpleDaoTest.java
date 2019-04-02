package org.nutz.dao.test.normal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.nutz.Nutz;
import org.nutz.castor.Castors;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.FieldMatcher;
import org.nutz.dao.Sqls;
import org.nutz.dao.TableName;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.MappingField;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.DaoExecutor;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.sql.NutStatement;
import org.nutz.dao.jdbc.JdbcExpert;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.DaoStatement;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.test.DaoCase;
import org.nutz.dao.test.meta.A;
import org.nutz.dao.test.meta.Abc;
import org.nutz.dao.test.meta.DynamicTable;
import org.nutz.dao.test.meta.Master;
import org.nutz.dao.test.meta.Pet;
import org.nutz.dao.test.meta.PetObj;
import org.nutz.dao.test.meta.SimplePOJO;
import org.nutz.dao.test.meta.UseBlobClob;
import org.nutz.dao.test.meta.issue396.Issue396Master;
import org.nutz.dao.test.meta.issue726.Issue726;
import org.nutz.dao.test.meta.issue901.XPlace;
import org.nutz.dao.test.meta.issue918.Region;
import org.nutz.dao.test.meta.issue928.BeanWithSet;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.blob.SimpleBlob;
import org.nutz.dao.util.blob.SimpleClob;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;

public class SimpleDaoTest extends DaoCase {

    public void before() {
        dao.create(Pet.class, true);
    }

    private void insertRecords(int len) {
        for (int i = 0; i < len; i++) {
            Pet pet = Pet.create("pet" + i);
            pet.setNickName("alias_" + i);
            pet.setPrice((float)(R.random(30, 100) / Math.PI));
            dao.insert(pet);
        }
    }

    /**
     * for issue #675 提供一个直接返回对象的方法
     */
    @Test
    public void test_dao_func() {
        insertRecords(10);

        int n = dao.func(Pet.class, "SUM", "price");
        assertTrue(n > 0);

        Object o = dao.func2(Pet.class, "SUM", "price");
        assertTrue((o instanceof Number));
        assertTrue(((Number) o).floatValue() > 0.0f);
    }

    /**
     * for issue #515 写给 mysql 一个特殊的例子
     */
    @Test
    public void test_escape_char() {
        if (dao.meta().isMySql()) {
            dao.insert(Pet.create("A").setNickName("AAA"));
            dao.insert(Pet.create("B").setNickName("B%B"));

            Criteria cri = Cnd.cri();
            cri.where().andLike("alias", "\\%");
            List<Pet> pets = dao.query(Pet.class, cri);
            assertEquals(1, pets.size());
            assertEquals("B", pets.get(0).getName());
        }
    }

    @Test
    public void test_simple_fetch_record() {
        Pet pet = Pet.create("abc");
        long now = System.currentTimeMillis();
        pet.setBirthday(Castors.me().castTo(now, Timestamp.class));
        dao.insert(pet);

        List<Record> pets = dao.query("t_pet", null, null);
        assertEquals(1, pets.size());
        assertEquals("abc", pets.get(0).getString("name"));
        assertEquals(now / 1000, pets.get(0).getTimestamp("birthday").getTime() / 1000);
    }

    @Test
    public void test_delete_list() {
        insertRecords(8);
        List<Pet> list = dao.query(Pet.class, null, null);
        List<Pet> pets = new ArrayList<Pet>(list.size());
        pets.addAll(list);
        assertEquals(8, pets.size());
        pets.addAll(list);
        dao.delete(pets);
        assertEquals(0, dao.count(Pet.class));
    }

    @Test
    public void test_simple_update() {
        dao.fastInsert(Lang.array(Pet.create("A"), Pet.create("B")));
        Pet a = dao.fetch(Pet.class, "A");
        a.setName("C");
        a.setAge(5);

        dao.update(a);

        Pet c = dao.fetch(Pet.class, "C");
        assertEquals("C", c.getName());
        assertEquals(5, c.getAge());

        Pet b = dao.fetch(Pet.class, "B");
        assertEquals("B", b.getName());
    }

    @Test
    public void test_fetch_by_condition_in_special_char() {
        dao.insert(Pet.create("a@b").setNickName("ABC"));
        Pet pet = dao.fetch(Pet.class, Cnd.where("name", "=", "a@b"));
        assertEquals("a@b", pet.getName());
        assertEquals("ABC", pet.getNickName());
    }

    @Test
    public void test_count_with_entity() {
        insertRecords(8);
        int re = dao.count(Pet.class, new Condition() {
            public String toSql(Entity<?> entity) {
                return entity.getField("nickName").getColumnName() + " IN ('alias_5','alias_6')";
            }
        });
        assertEquals(2, re);
    }

    @Test
    public void test_table_exists() {
        assertTrue(dao.exists(Pet.class));
    }

    @Test
    public void test_count_by_condition() {
        insertRecords(4);
        assertEquals(4, dao.count(Pet.class));
        assertEquals(2, dao.count(Pet.class, Cnd.wrap("name IN ('pet2','pet3') ORDER BY name ASC")));
    }

    @Test
    public void run_2_sqls_with_error() {
        assertEquals(0, dao.count(Pet.class));
        Sql sql1 = Sqls.create("INSERT INTO t_pet (name) VALUES ('A')");
        Sql sql2 = Sqls.create("INSERT INTO t_pet (nocol) VALUES ('B')");
        try {
            dao.execute(sql1, sql2);
            fail();
        }
        catch (DaoException e) {}
        assertEquals(0, dao.count(Pet.class));
    }

    @Test
    public void test_clear_two_records() {
        dao.insert(Pet.create("A"));
        dao.insert(Pet.create("B"));
        assertEquals(2, dao.clear(Pet.class, Cnd.where("id", ">", 0)));
        assertEquals(0, dao.clear(Pet.class, Cnd.where("id", ">", 0)));
    }

    @Test
    public void test_delete_records() {
        dao.insert(Pet.create("A"));
        dao.insert(Pet.create("B"));
        assertEquals(1, dao.delete(Pet.class, "A"));
        assertEquals(1, dao.delete(Pet.class, "B"));
        assertEquals(0, dao.delete(Pet.class, "A"));
    }

    @Test
    public void test_integer_object_column() {
        dao.insert(PetObj.create("X"));
        PetObj pet = dao.fetch(PetObj.class, "X");

        assertEquals("X", pet.getName());
        assertNull(pet.getAge());

        dao.update(pet.setAge(20));
        pet = dao.fetch(PetObj.class, "X");
        assertEquals(20, pet.getAge().intValue());

        dao.update(pet.setAge(null));
        pet = dao.fetch(PetObj.class, "X");
        assertNull(pet.getAge());
    }

    @Test
    public void test_insert_readonly() {
        dao.create(SimplePOJO.class, true);
        SimplePOJO p = new SimplePOJO();
        p.setSex("火星");
        dao.insert(p);
        p.setSex("东方不败");
        dao.update(p);
    }

    @Test
    public void test_order_by() {
        dao.create(Abc.class, true);
        Abc a = new Abc();
        a.setName("ccc");
        dao.insert(a);
        a.setName("abc");
        dao.insert(a);
        dao.query(Abc.class, Cnd.where("id", ">", "-1").asc("name"), null);
    }

    @Test
    public void test_clear() {
        dao.create(Pet.class, true);
        dao.insert(Pet.create("Wendal"));
        dao.insert(Pet.create("Wendal2"));
        dao.insert(Pet.create("Wendal3"));
        dao.insert(Pet.create("Wendal4"));
        dao.insert(Pet.create("Wendal5"));
        assertEquals(5, dao.count(Pet.class));
        assertEquals(5, dao.clear(Pet.class));
    }

    @Test
    public void test_chain_insert() {
        dao.insert(Pet.class, Chain.make("name", "wendal").add("nickName", "asfads"));
    }

    @Test
    public void test_sql_pager() {
        dao.create(Pet.class, true);
        for (int i = 0; i < 100; i++) {
            dao.insert(Pet.class,
                       Chain.make("name", "record" + i).add("nickName",
                                                            "Time=" + System.currentTimeMillis()));
        }
        Pager pager = dao.createPager(5, 5);
        pager.setRecordCount(dao.count(Pet.class));
        Sql sql = Sqls.queryEntity("select * from t_pet");
        sql.setEntity(dao.getEntity(Pet.class));
        sql.setPager(pager);
        dao.execute(sql);

        List<Pet> pets = sql.getList(Pet.class);
        assertNotNull(pets);
        assertEquals(5, pets.size());
        assertEquals("record20", pets.get(0).getName());
        assertEquals("record21", pets.get(1).getName());
        assertEquals("record22", pets.get(2).getName());
        assertEquals("record23", pets.get(3).getName());
        assertEquals("record24", pets.get(4).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_fetch_null_name() {
        dao.fetch(Pet.class, (String) null);
    }

    @Test(expected = Exception.class)
    public void test_create_error_class() {
        dao.create(Nutz.class, true);
    }

    // issue 395 删除一个不存在的管理对象
    @Test
    public void test_delete_null_many() {
        dao.create(Master.class, true);
        Master master = new Master();
        master.setName("ACB");
        dao.insert(master);
        master = dao.fetch(Master.class);
        dao.fetchLinks(master, null);
        dao.deleteWith(master, null);
    }

    // issue 396
    @Test
    public void test_insert_with() {
        if (!dao.meta().isOracle())
            return;
        dao.create(Issue396Master.class, true);
    }

    @Test
    public void test_insert_special_chain() {
        if (dao.meta().isMySql())
            dao.insert(Pet.class, Chain.makeSpecial("birthday", "now()").add("name", "wendal"));
    }

    @Test
    public void test_issue_726() {
        dao.create(Issue726.class, true);
        assertTrue(dao.getEntity(Issue726.class).getColumn("id").isAutoIncreasement());
    }

    @Test
    public void test_daoex_update() throws Throwable {
        final List<A> list = new ArrayList<A>();
        for (int i = 0; i < 1; i++) {
            A a = new A();
            a.setUid(System.currentTimeMillis());
            Lang.quiteSleep(10);
            // a.setName("zzzz" + System.currentTimeMillis());
            list.add(a);
        }
        TableName.run("1", new Runnable() {
            public void run() {
                dao.create(A.class, true);
                dao.update(list);
            }
        });
        System.out.println("\n\n\n\n\n\n\n\n");
        Daos.ext(dao, FieldFilter.create(A.class, null, "^(name)$", true), 1).update(list);

    }

    @Test
    public void test_bean_uuid() {
        Sql sql = Sqls.queryRecord("select * from t_pet");
        sql.setPager(dao.createPager(1, 10));
        dao.execute(sql);
    }

    @Test
    public void test_fetchLinks() {
        Master master = new Master();
        master.setName("wendal");
        Pet pet = Pet.create("asdfs");
        Pet pet2 = Pet.create("zzzz");
        List<Pet> pets = new ArrayList<Pet>();
        pets.add(pet);
        pets.add(pet2);
        master.setPets(pets);
        dao.insertWith(master, null);
        List<Master> list = dao.query(Master.class, null);
        dao.fetchLinks(list, null, Cnd.where("1", "=", 1));
    }

    @Test
    public void test_insert_with_id() {
        dao.clear(Pet.class);
        Pet pet = Pet.create("zzz");
        pet.setId(9090); // 主动设置id
        Dao dao = Daos.ext(this.dao, FieldFilter.create(Pet.class,
                                                        FieldMatcher.make(null, null, true)
                                                                    .setIgnoreId(false)));
        dao.fastInsert(pet);
        pet = dao.fetch(Pet.class); // 只有一条记录
        assertEquals(9090, pet.getId());

        // / 然后用1.b.53的新方法测试一下
        if (dao.meta().isPostgresql()) {
            System.out.println("因为Pet的@Id配置了@Next,导致插入后再执行里面的sql会报错");
            // 还没想到怎么解决, FieldMatcher存在的时候忽略@Next?
            return;
        }

        dao.clear(Pet.class);
        pet = Pet.create("zzz");
        pet.setId(9090); // 主动设置id
        dao.insert(pet, FieldFilter.create(Pet.class, FieldMatcher.create(false)));
        pet = dao.fetch(Pet.class); // 只有一条记录
        assertEquals(9090, pet.getId());
    }

    @Test
    public void test_use_blob_clob() {
        dao.create(UseBlobClob.class, true);
        UseBlobClob use = new UseBlobClob();
        use.setName("wendal");
        use.setX(new SimpleBlob(Files.findFile("nutz-test.properties")));
        use.setY(new SimpleClob(Files.findFile("nutz-test.properties")));
        use = dao.insert(use);

        use.setX(new SimpleBlob(Files.findFile("log4j.properties")));
        use.setY(new SimpleClob(Files.findFile("log4j.properties")));
        dao.update(use);
    }

    @Test
    public void test_migration() {
        dao.execute(Sqls.create("drop table t_pet"));
        Entity<Pet> en = dao.getEntity(Pet.class);
        NutDao dao = (NutDao) this.dao;
        JdbcExpert expert = dao.getJdbcExpert();
        MappingField mf = en.getField("age");
        String str = "create table t_pet ("
                     + mf.getColumnName()
                     + " "
                     + expert.evalFieldType(mf)
                     + ","
                     + mf.getColumnName()
                     + "_2"
                     + " "
                     + expert.evalFieldType(mf)
                     + ")";
        dao.execute(Sqls.create(str));

        Daos.migration(dao, Pet.class, true, true);

    }

    @Test
    public void test_dynamic_migration() {
        for (int i = 0; i < 3; i++) {
            Daos.ext(dao, i).create(DynamicTable.class, true);
        }

        for (int i = 0; i < 3; i++) {
            dao.execute(Sqls.createf("ALTER table dynamic_table%s DROP column createTime", i));
            Daos.migration(dao, DynamicTable.class, true, true, i);
        }

    }
    
    @Test
    public void test_varchar_BigDecimal() {
        dao.create(XPlace.class, true);
        XPlace place = new XPlace();
        place.setLat(new BigDecimal("12.3222"));
        place.setLng(new BigDecimal("29.02333"));
        dao.insert(place);
        
        place = dao.fetch(XPlace.class);
        assertEquals("12.3222", place.getLat().toString());
        assertEquals("29.02333", place.getLng().toString());
        
        System.out.println(Json.toJson(place));
    }
    
    @Test
    public void test_xxx() {
        Sql task_sql= Sqls.create("SELECT * FROM act_ru_task WHERE CATEGORY_=@category AND ( ASSIGNEE_=@userId  $assignee ) ORDER BY create_time_ desc");
        task_sql.params().set("category",1);
        task_sql.params().set("userId", 2);
        task_sql.vars().set("assignee", "and name != 'hi'");

        System.out.println(task_sql.toPreparedStatement());
        System.out.println(task_sql.forPrint());
        System.out.println(">>"+task_sql);
    }
    
    @Test
    public void test_issue_918() {
        dao.create(Region.class, true); 
    }
    
    @Test
    public void test_issue_928() {
        dao.create(BeanWithSet.class, true);
        BeanWithSet s = new BeanWithSet();
        Set<Long> uids = new HashSet<Long>();
        Long UID = 1234455656L;
        uids.add(UID);
        s.setUids(uids);
        
        Set<String> names = new HashSet<String>();
        names.add("wendal");
        s.setNames(names);
        System.out.println(names);
        
        dao.insert(s);
        
        BeanWithSet out = dao.fetch(BeanWithSet.class);
        assertEquals(Long.class, out.getUids().iterator().next().getClass());
        assertEquals(UID, out.getUids().iterator().next());
        
    }
    
    @Test
    public void test_query_2() {
        dao.insert(Pet.create(100));
        List<Pet> list = dao.query(Pet.class, null);
        for (Pet pet : list) {
            assertNotNull(pet.getName());
        }
    }
    
    @Test
    public void test_get_sql() throws Throwable {
        NutDao dao = new NutDao(ioc.get(javax.sql.DataSource.class));
        final List<String> sqls = new ArrayList<String>();
        final Method m = NutStatement.class.getDeclaredMethod("toStatement", Object[][].class, String.class);
        m.setAccessible(true);
        dao.setExecutor(new DaoExecutor() {
            public void exec(Connection conn, DaoStatement st) {
                String psql = st.toPreparedStatement();
                sqls.add(psql);
                // 如果需要带数据的, 因为nutz并不生成和执行带数据的sql,所以需要通过
                // st.toPreparedStatement() 与 参数矩阵 st.getParamMatrix() 综合生成
                // 这里调用非公开api中的toStatement
                // 事实上根据参数矩阵(getParamMatrix)和Sql语句(toPreparedStatement)就能逐一替换生成
                try {
                    String ssql = (String) m.invoke(st, st.getParamMatrix(), psql);
                    sqls.add(ssql);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dao.insert(Pet.create("hi"));
        for (String sql : sqls) {
            System.out.println(sql);
        }
    }
}
