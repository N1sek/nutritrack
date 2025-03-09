package org.nutritrack.nutritrack.dto;

import org.nutritrack.nutritrack.enums.Rol;

public record UserDTO(Long id, String nickname, String email, Rol rol) {}