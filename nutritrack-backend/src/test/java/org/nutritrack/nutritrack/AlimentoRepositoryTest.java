package org.nutritrack.nutritrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nutritrack.nutritrack.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AlimentoRepositoryTest {

    @Autowired
    private AlimentoRepository alimentoRepository;


}
