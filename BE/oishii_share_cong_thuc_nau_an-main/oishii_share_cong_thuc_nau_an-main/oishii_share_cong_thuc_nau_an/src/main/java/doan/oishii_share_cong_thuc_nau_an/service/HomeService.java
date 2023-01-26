package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.exception.BadRequestException;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileEditRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import org.springframework.http.ResponseEntity;

public interface HomeService {
    ProfileRequest getProfile(Integer username);

    ResponseEntity<?> updateProfile(Integer profileId, ProfileEditRequest profileRequest) throws BadRequestException;

    ResponseEntity<?> updateImage(Integer profileId, String image);
}
