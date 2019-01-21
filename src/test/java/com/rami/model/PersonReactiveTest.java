package com.rami.model;

import com.rami.command.PersonCommand;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class PersonReactiveTest {

    Logger LOG = LoggerFactory.getLogger(getClass());

    final Person paulina = new Person("Paulina","VP");
    final Person rami = new Person("Rami","Del Toro");


    @Test
    public void monoTests() {
        //Create Mono
        final Mono<Person> paulinaMono = Mono.just(paulina);

        //Get Object from Mono
        final Person paulinaFromMono = paulinaMono.block();

        LOG.info(paulinaFromMono.sayMyName());

    }

    @Test
    public void monoTransform() {
        final Mono<Person> ramiMono = Mono.just(rami);

        final PersonCommand ramiCommandFromMono = ramiMono.map(person -> {
            return new PersonCommand(person);
        }).block();

        LOG.info(ramiCommandFromMono.sayMyName());
    }

    @Test(expected = NullPointerException.class)
    public void monoFilterWithNull() {
        final Mono<Person> ramiMono = Mono.just(rami);

        final Person sam = ramiMono.filter(person -> person.getFirstName().equalsIgnoreCase("foo"))
                .block();

        LOG.info("Sam say my name = {}",sam.sayMyName());
    }

    @Test
    public void monoFilter() {
        final Mono<Person> ramiMono = Mono.just(rami);

        final Person newrami = ramiMono.filter(person -> person.getFirstName().equalsIgnoreCase("rami"))
                .block();

        LOG.info("Sam say my name = {}",newrami.sayMyName());
    }

    @Test
    public void fluxTest() {
        final Flux<Person> people = Flux.just(rami,paulina);

        people.subscribe(person -> LOG.info(person.sayMyName()));
    }

    @Test
    public void fluxTestFilter() {
        final Flux<Person> people = Flux.just(rami,paulina);

        people.filter(person -> person.getFirstName().equalsIgnoreCase("Rami"))
                .subscribe(person -> LOG.info("Person = {}",person.sayMyName()));
    }

    @Test
    public void fluxTestDelayWithoutOutput() {
        final Flux<Person> people = Flux.just(rami,paulina);

        /*
              1. Emit every second
              2. Dont wait for output.
         */
        people.delayElements(Duration.ofSeconds(1))
                .subscribe(person -> LOG.info("Person = {}",person.sayMyName()));
    }

    @Test
    public void fluxTestWithOutput() throws InterruptedException {
        final Flux<Person> people = Flux.just(rami,paulina);
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        people.delayElements(Duration.ofSeconds(1))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(person -> LOG.info("Person = {}",person.sayMyName()));

        countDownLatch.await();
    }

    @Test
    public void fluxTestFilterDelay() throws InterruptedException {
        final Flux<Person> people = Flux.just(rami,paulina);
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        people.delayElements(Duration.ofSeconds(1))
                .filter(person -> person.getFirstName().contains("m"))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(person -> LOG.info("Person = {}",person.sayMyName()));

        countDownLatch.await();
    }




}