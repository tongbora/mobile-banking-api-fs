package org.istad.mobilebankingfs.mapper;
import org.istad.mobilebankingfs.domain.Customer;
import org.istad.mobilebankingfs.domain.KYC;
import org.istad.mobilebankingfs.domain.Segment;
import org.istad.mobilebankingfs.dto.customer.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse toCustomerResponse(Customer customer);
//    Customer fromCreateCustomerRequest(CreateCustomerRequest request);
//    Customer fromCustomerUpdate(CustomerUpdate update);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(CustomerUpdate update, @MappingTarget Customer customer);

    // For Segment
    SegmentResponse toSegmentResponse(Segment segment);

    // For KYC
    KYCResponse toKYCResponse(KYC kyc);
}
