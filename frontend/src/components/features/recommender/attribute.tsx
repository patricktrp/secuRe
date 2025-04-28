type AttributeProps = {
  heading: string;
  content: string;
};

const Attribute: React.FC<AttributeProps> = ({ heading, content }) => {
  return (
    <div>
      <div className="font-semibold">{heading}</div>
      <div className="text-muted-foreground">{content}</div>
    </div>
  );
};

export default Attribute;
