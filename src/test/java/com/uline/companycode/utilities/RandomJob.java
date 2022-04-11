package com.uline.companycode.utilities;

import com.github.javafaker.Faker;
import com.uline.companycode.pojos.JobPojo;

public class RandomJob {
    public static Faker faker = new Faker();

    public static JobPojo getRandomJob() {
        JobPojo job = new JobPojo();
        job.setCompany(faker.company().name());
        job.setPosition(faker.job().position());

        return job;
    }
}
