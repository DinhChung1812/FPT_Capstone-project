import { CheckCircleTwoTone } from "@ant-design/icons";
import { notification } from "antd";

export default function openNotification(message) {

    notification.open({
        message: message,
        icon: (
            <CheckCircleTwoTone
                style={{
                    color: '#108ee9',
                }}
            />
        ),
    });
};
