type UserMessageProps = {
  content: string;
};

const UserMessage: React.FC<UserMessageProps> = ({ content }) => {
  return (
    <div className="flex justify-end">
      <div className="py-2 px-4 bg-sidebar rounded-lg">{content}</div>
    </div>
  );
};

export default UserMessage;
