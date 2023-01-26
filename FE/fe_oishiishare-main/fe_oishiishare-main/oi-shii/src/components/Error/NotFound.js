import React from 'react';
import {
  Flex,
  Heading,
  VStack,
  Image,
  Button,
  Text,
  HStack,
} from '@chakra-ui/react'
import ErrorImg from '../../img/error.png';
import { useNavigate,Link }   from 'react-router-dom'
import routes from '../../constans/utils/routes';



const NotFound = (props) => {
    const history = useNavigate();
  return (
    <Flex
      minH={450}
      justifyContent="center"
      backgroundColor="#fff"
      alignItems="center"
      w="100%"
    >
      <HStack>
        <Image src={ErrorImg} w="350px" padding={30} />
        <VStack>
          <Heading>Error</Heading>
          <Text fontWeight="bold" >Sorry!! We could not find the page you were looking for.</Text>
          <h4 style={{color: 'green'}}>Oh! Có vẻ bạn đã nhập không chính xác ở đâu đó, hãy thử lại xem sao!!!</h4>
          <Link className="text-back" to="/" >Back to Home</Link>
        </VStack>
      </HStack>
    </Flex>

  );
}

export default NotFound;