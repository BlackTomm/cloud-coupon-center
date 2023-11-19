package org.coding.protobuf;


import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AddressBookProtosTest {

    @Test
    public void bufTest() throws IOException {
        String email = "j@baeldung.com";
        int id = new Random().nextInt();
        String name = "Michael Program";
        String number = "01234567890";
        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .addNumbers(number)
                        .build();

        assertEquals(person.getEmail(), email);
        assertEquals(person.getId(), id);
        assertEquals(person.getName(), name);
        assertEquals(person.getNumbers(0), number);


        String filePath = "./message.txt";
        AddressBookProtos.AddressBook addressBook
                = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();
        FileOutputStream fos = new FileOutputStream(filePath);
        addressBook.writeTo(fos);

//        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
//        writer.append(addressBook.toString());
//        writer.close();

        AddressBookProtos.AddressBook deserialized
                = AddressBookProtos.AddressBook.newBuilder()
                .mergeFrom(new FileInputStream(filePath)).build();

        assertEquals(deserialized.getPeople(0).getEmail(), email);
        assertEquals(deserialized.getPeople(0).getId(), id);
        assertEquals(deserialized.getPeople(0).getName(), name);
        assertEquals(deserialized.getPeople(0).getNumbers(0), number);
    }
}