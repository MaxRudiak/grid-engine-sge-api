package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/**
 * Enumeration that contains values of attribute "name" of tag "hostvalue" of host xml representation.
 */
@XmlEnum
@XmlAccessorType(XmlAccessType.FIELD)
public enum SGEHostProperty {

    /**
     * A type of system architecture.
     */
    @XmlEnumValue("arch_string")
    TYPE_OF_ARCHITECT,

    /**
     * Number of processors provided by host.
     */
    @XmlEnumValue("num_proc")
    NUM_OF_PROCESSORS,

    /**
     * A medium time average OS runs queue length.
     */
    @XmlEnumValue("load_avg")
    LOAD,

    /**
     * Total amount of memory.
     */
    @XmlEnumValue("mem_total")
    MEM_TOTAL,

    /**
     * Total amount of use memory.
     */
    @XmlEnumValue("mem_used")
    MEM_USED,

    /**
     * Total amount of swap space
     * (memory in form of a partition or a file).
     */
    @XmlEnumValue("swap_total")
    TOTAL_SWAP_SPACE,

    /**
     * Total Amount of used swap space.
     */
    @XmlEnumValue("swap_used")
    USED_SWAP_SPACE
}
