package de.rwth_aachen.swc.recsec;

import org.springframework.boot.SpringApplication;

public class TestRecsecApplication {

	public static void main(String[] args) {
		SpringApplication.from(SecuReApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
