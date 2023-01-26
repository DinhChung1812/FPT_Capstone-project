import React from 'react';
import { Box, Flex, Text, VStack } from '@chakra-ui/react';

export default function Footer() {
    return (
        <Flex p={10} justifyContent="center" boxShadow="inner" >
            <VStack w="75%">
                <Box p={5}>
                    <Text fontWeight="bold">Made with love ❤️</Text>
                </Box>
                <Text>Đại học FPT, Khu công nghệ cao Hòa Lạc - Km29, ĐCT08, Thạch Hoà, Thạch Thất, Hà Nội</Text>
                <Text>Phone: 0398860322</Text>
                <Text>Email: oishii@gmail.com</Text>
            </VStack>
        </Flex>
    );
}