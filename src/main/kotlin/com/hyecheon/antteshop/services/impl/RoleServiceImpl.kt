package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.mapper.RoleMapper
import com.hyecheon.antteshop.repositories.RoleRepository
import com.hyecheon.antteshop.services.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository,
) : RoleService {
    val mapper: RoleMapper = RoleMapper.INSTANCE!!
    override fun findAll() = run {
        val roles = roleRepository.findAll()
        roles.map(mapper::toDto)
    }
}