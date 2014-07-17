package org.tw.spike.sd.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.UUID;

public class App {

    private static Cluster cluster;
    private static Session session;

    public static void main(String[] args) {

        cluster = Cluster.builder().addContactPoints("192.168.33.42").build();

        session = cluster.connect("spike");

        CassandraOperations cassandraOps = new CassandraTemplate(session);

        cassandraOps.insert(new Person(UUID.randomUUID().toString(), "David", 20));

        Select s = QueryBuilder.select().from("person");
        s.where(QueryBuilder.eq("id", "1234567890"));

        System.out.println(cassandraOps.queryForObject(s, Person.class));

        //cassandraOps.truncate("person");

    }
}