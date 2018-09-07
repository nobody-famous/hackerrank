CC 			:= javac

TARGET 		:= Solution

SRCDIR 		:= src
TARGETDIR	:= classes
SRCEXT		:= java
OBJEXT		:= class

CFLAGS		:= 

SOURCES		:= $(shell find $(SRCDIR) -type f -name *.$(SRCEXT))
OBJECTS		:= $(patsubst $(SRCDIR)/%, $(BUILDDIR)/%, $(SOURCES:.$(SRCEXT)=.$(OBJEXT)))

all: directories $(TARGET)

run_00: all
	java -cp $(TARGETDIR) $(TARGET) < input/input00.txt

run_01: all
	java -cp $(TARGETDIR) $(TARGET) < input/input01.txt

run_05: all
	time java -cp $(TARGETDIR) $(TARGET) < input/input05.txt

run_sample: all
	java -cp $(TARGETDIR) $(TARGET) < input/sample.txt

rebuild: clean all

directories:
	@mkdir -p $(TARGETDIR)

clean:
	@$(RM) -rf $(TARGETDIR)

$(TARGET): $(SOURCES)
	$(CC) -d $(TARGETDIR) $^

.PHONY: all clean run
